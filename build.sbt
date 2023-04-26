name := "books"

version := "0.1"

scalaVersion := "2.13.5"

val akkaVersion = "2.8.0"
val akkaHttpVersion = "10.5.1"

libraryDependencies ++= Seq(
  // akka streams
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  // akka http
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-cluster-typed" % akkaVersion,

  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
)