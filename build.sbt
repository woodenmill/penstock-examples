name := "penstock-examples"

version := "0.0.1"

scalaVersion := "2.12.7"

resolvers += Resolver.bintrayRepo("woodenmill", "oss-maven")

libraryDependencies += "io.woodenmill" %% "penstock" % "0.0.4"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "1.1.1" % Test



val circeVersion = "0.10.0"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)
