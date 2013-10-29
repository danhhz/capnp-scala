import sbt._
import Keys._
import com.capnproto.plugin.CapnpCodegenPlugin
import sbtassembly.{Plugin => SbtAssemblyPlugin}
import sbtassembly.Plugin.AssemblyKeys._

object CapnpScalaBuild extends Build {
  override lazy val settings = super.settings ++ Seq(
  	name := "capnp-scala",
    organization := "com.capnproto",
  	version := "0.0.1-SNAPSHOT",
  	scalaVersion := "2.10.3",
    resolvers += twitterRepo
  )

  lazy val rogueField = "com.foursquare" %% "rogue-field" % "2.2.1"
  lazy val twitterRepo = "Twitter Repo" at "http://maven.twttr.com"
  lazy val twitterServer = "com.twitter" %% "twitter-server" % "1.0.2"
  lazy val twitterFinatra = "com.twitter" % "finatra" % "1.4.0"

  lazy val commonProjectSettings = Project.defaultSettings ++ Seq(
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xfatal-warnings", "-feature")
  )

  lazy val codegenSbtSettings = commonProjectSettings
  lazy val codegenSbtPlugin = Project(
    id = "capnp-codegen-plugin",
    base = file("plugin"),
    settings = codegenSbtSettings ++ Seq(
      sbtPlugin := true
    )
  )

  lazy val runtimeSettings = commonProjectSettings
  lazy val runtime = Project(
    id = "runtime",
    base = file("runtime"),
    settings = runtimeSettings ++ Seq(
      libraryDependencies ++= Seq(rogueField, twitterServer, twitterFinatra)
    )
  )

  lazy val codegenSettings = commonProjectSettings ++
    SbtAssemblyPlugin.assemblySettings ++
    addArtifact(Artifact("capnp-scala-codegen", "assembly"), SbtAssemblyPlugin.AssemblyKeys.assembly)
  lazy val codegen = Project(
    id = "codegen",
    base = file("codegen"),
    settings = codegenSettings ++ Seq(
      jarName.in(assembly) <<= (name, scalaVersion, version).map({ _ + "_" + _ + "-" + _ + "-assembly.jar" })
    )
  ).dependsOn(runtime)

  lazy val examplesSettings = commonProjectSettings ++
    CapnpCodegenPlugin.capnpSettings
  lazy val examples = Project(
    id = "examples",
    base = file("examples"),
    settings = examplesSettings
  ).dependsOn(runtime)
}
