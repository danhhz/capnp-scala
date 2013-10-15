// import AssemblyKeys._

name := "capnp-scala"
 
version := "0.0.1"
 
scalaVersion := "2.9.1"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xfatal-warnings")

// assemblySettings

// mainClass in assembly := Some("com.capnproto.codegen.CapnpScala")

mainClass in (Compile, run) := Some("com.capnproto.codegen.CapnpScala")

resolvers += "repo.codahale.com" at "http://repo.codahale.com"
 
libraryDependencies += "com.codahale" % "jerkson_2.9.1" % "0.5.0"

libraryDependencies += "com.foursquare" %% "spindle-runtime" % "1.4.2"
