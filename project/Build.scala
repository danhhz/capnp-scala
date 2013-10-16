import sbt._
import Keys._

object CapnpScalaBuild extends Build {
  override lazy val settings = super.settings ++ Seq(
  	name := "capnp-scala",
  	version := "0.0.1",
  	scalaVersion := "2.10.3"
  )

  lazy val commonProjectSettings = Project.defaultSettings ++ Seq(
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xfatal-warnings")
  )

  lazy val runtimeSettings = commonProjectSettings
  lazy val runtime = Project(id = "runtime", base = file("runtime"), settings = runtimeSettings)

  lazy val codegenSettings = commonProjectSettings ++ Seq(
  	mainClass.in(Compile, run) := Some("com.capnproto.codegen.CapnpScala")
  )
  lazy val codegen = Project(id = "codegen", base = file("codegen"), settings = codegenSettings).dependsOn(runtime)

  lazy val examplesSettings = commonProjectSettings
  lazy val examples = Project(id = "examples", base = file("examples"), settings = examplesSettings).dependsOn(runtime)
}
