name := "penstock-examples"

version := "0.0.1"

scalaVersion := "2.12.7"

resolvers += Resolver.bintrayRepo("woodenmill", "oss-maven")

libraryDependencies += "io.woodenmill" %% "penstock" % "0.0.2" % Test

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "1.1.1" % Test
libraryDependencies += "com.softwaremill.sttp" %% "core" % "1.3.0" % Test


val circeVersion = "0.10.0"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)
