// Copyright 2013 Foursquare Labs Inc. All Rights Reserved.

package com.capnproto.plugin

import sbt._
import sbt.Fork.ForkJava
import sbt.Keys.TaskStreams
import java.io.{File, FileWriter}
import scala.language.postfixOps

object CapnpCodegenPlugin extends Plugin {
  val Capnp = config("capnp").hide

  val capnp = TaskKey[Seq[File]]("capnp", "Generate Scala sources from Capnp files(s)")
  val capnpCodegenVersion = SettingKey[String]("capnp-codegen-version", "Version of Capnp codegen to use.")
  val capnpCodegenBinaryLibs = SettingKey[Seq[ModuleID]]("capnp-codegen-binary-libs", "Version of Capnp codegen binary to use.")
  val capnpCodegenIncludes = SettingKey[Seq[File]]("capnp-codegen-includes", "Directories to include in Capnp dependency resolution.")

  val capnpSettings = Seq(
    Keys.ivyConfigurations += Capnp,
    capnpCodegenVersion := "0.0.1-SNAPSHOT",
    capnpCodegenBinaryLibs <<= (capnpCodegenVersion, Keys.scalaVersion)((v, _) => {
      Seq("com.capnproto" %% "codegen" % v)
    }),
    Keys.libraryDependencies <++= (capnpCodegenBinaryLibs, capnpCodegenBinaryLibs)((binary, _) => {
      binary.map(_ % "capnp")
    })
  ) ++ capnpSettingsIn(Compile) ++ capnpSettingsIn(Test)

  def capnpSettingsIn(conf: Configuration): Seq[Def.Setting[_]] =
    inConfig(conf)(capnpSettingsRaw) ++ Seq(
      Keys.clean <<= Keys.clean.dependsOn(Keys.clean in capnp in conf),
      Keys.sourceDirectory in capnp in conf <<= (Keys.sourceDirectory in conf)(_ / "capnp"),
      Keys.sources in capnp in conf <<= (Keys.sourceDirectory in capnp in conf).map(dir => (dir ** "*.capnp").get),
      capnpCodegenIncludes in conf <<= (Keys.sourceDirectory in capnp in conf)(dir => Seq(dir))
    )

  private def capnpSettingsRaw = Seq[Def.Setting[_]](
    Keys.sourceManaged in capnp ~= (_ / "capnp"),
    capnp <<= (
      Keys.sources in capnp, Keys.classpathTypes in capnp, Keys.update,
      Keys.sourceManaged in capnp, capnpCodegenIncludes, Keys.resolvedScoped,
      Keys.baseDirectory, Keys.streams
    ).map(capnpCompile),
    Keys.sourceGenerators <+= capnp,
    Keys.clean in capnp <<= (
      Keys.sourceManaged in capnp, Keys.resolvedScoped, Keys.streams
    ).map(capnpClean)
  )

  /**
   * @return the .scala files in `sourceManaged` after compilation.
   */
  private def capnpCompile(
      capnpSources: Seq[File],
      classpathTypes: Set[String],
      updateReport: UpdateReport,
      sourceManaged: File,
      includes: Seq[File],
      resolvedScoped: Def.ScopedKey[_],
      baseDirectory: File,
      streams: TaskStreams
  ): Seq[File] = {
    import streams.log
    def generated = (sourceManaged ** "*.scala").get

    val shouldProcess = (capnpSources, generated) match {
      case (Seq(), _) => log.debug("No sources, skipping."); false
      case (_, Seq()) => log.debug("No products, generating."); true
      case (ins, outs) =>
        val inLastMod = ins.map(_.lastModified()).max
        val outLasMod = outs.map(_.lastModified()).min
        log.debug("Sources last modified: %s. Products last modified: %s.".format(inLastMod, outLasMod))
        outLasMod < inLastMod
    }

    def createCapnpScript: File = {
      val capnpScalaScript = File.createTempFile("capnpc-scala", "")
      val classpathParam = {
        val jars = Classpaths.managedJars(Capnp, classpathTypes, updateReport).map(_.data)
        jars.mkString(File.pathSeparator)
      }
      val mainClass = "com.capnproto.codegen.CapnpScala"
      val contents = (
        "#! /bin/bash" + "\n" +
        "java -classpath %s %s".format(classpathParam, mainClass) + "\n"
      )
      val writer = new FileWriter(capnpScalaScript)
      writer.write(contents)
      writer.flush()
      capnpScalaScript.setExecutable(true)
      capnpScalaScript
    }

    if (shouldProcess) {
      log.info("Compiling %d Capnp file(s) in %s".format(capnpSources.size, Def.displayFull(resolvedScoped)))
      sourceManaged.mkdirs()
      val capnpScalaPath = createCapnpScript.getAbsolutePath
      val capnpSourcePaths = capnpSources.map(_.getAbsolutePath)
      val cmd = Seq(
        "/usr/local/bin/capnp",
        "compile",
        "--src-prefix", baseDirectory.getAbsolutePath,
        "--output", "%s:%s".format(capnpScalaPath, sourceManaged.absolutePath)
      ) ++ capnpSourcePaths
      log.info("Capnp command line: " + cmd.mkString(" "))
      val returnCode = (cmd !)
      if (returnCode != 0) sys.error("Non zero return code from capnp [%d]".format(returnCode))
    } else {
      log.debug("No sources newer than products, skipping.")
    }

    generated
  }

  private def capnpClean(
      sourceManaged: File,
      resolvedScoped: Def.ScopedKey[_],
      streams: TaskStreams
  ): Unit = {
    import streams.log
    val filesToDelete = (sourceManaged ** "*.scala").get
    log.info("Cleaning %d Capnp generated files in %s".format(filesToDelete.size, Def.displayFull(resolvedScoped)))
    IO.delete(filesToDelete)
  }
}
