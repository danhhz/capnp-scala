name := "capnp-scala"
 
version := "0.0.1"
 
scalaVersion := "2.9.1"
 
resolvers += "repo.codahale.com" at "http://repo.codahale.com"
 
libraryDependencies += "com.codahale" % "jerkson_2.9.1" % "0.5.0"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xfatal-warnings")
