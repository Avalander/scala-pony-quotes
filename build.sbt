import Dependencies._

ThisBuild / scalaVersion     := "2.13.1"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.avalander"
ThisBuild / organizationName := "avalander"

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings,
    name := "pony-quotes",
    libraryDependencies ++= Seq(
      "org.mongodb.scala" %% "mongo-scala-driver" % "4.0.1",
      "com.typesafe.akka" %% "akka-http"   % "10.1.11",
      "com.typesafe.akka" %% "akka-stream" % "2.6.4",
      "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.11",
      scalaTest % "it,test",
      "com.typesafe.akka" %% "akka-stream-testkit" % "2.6.4" % Test,
      "com.typesafe.akka" %% "akka-http-testkit" % "10.1.11" % Test,
      "org.mockito" % "mockito-all" % "1.10.19" % Test
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
