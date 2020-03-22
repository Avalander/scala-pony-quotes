import Dependencies._

ThisBuild / scalaVersion     := "2.13.1"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.avalander"
ThisBuild / organizationName := "avalander"

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "1.0.0"

enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)

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

scalaJSUseMainModuleInitializer := true
jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()

lazy val web = (project in file("web"))
  .enablePlugins(
    ScalaJSPlugin,
    ScalaJSBundlerPlugin
  )
  .settings(
    name := "pony-quotes-web",
    jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv(),
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      scalaTest % Test,
      "org.scala-js" %%% "scalajs-dom" % "1.0.0",
      "com.lihaoyi" %%% "utest" % "0.7.4" % "test"
    ),
    testFrameworks += new TestFramework("utest.runner.Framework"),
    npmDependencies in Compile ++= Seq(
      "superfine" -> "7.0.0",
      "scalajs-friendly-source-map-loader" -> "0.1.4",
    ),
    webpackConfigFile in fastOptJS := Some(baseDirectory.value / "src" / "main" / "js" / "webpack.config.js"),
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
