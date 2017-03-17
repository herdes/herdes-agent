organization := "io.herdes.agent"
name := "herdes-agent"
version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= {
  val akkaV = "2.4.16"
  val akkaHttpV = "10.0.3"
  val scalazVersion = "7.1.9"
  Seq(
    "com.typesafe.akka"   %% "akka-actor"    % akkaV,
    "com.typesafe.akka"   %% "akka-multi-node-testkit" % "2.4.2",
    "com.typesafe.akka"   %% "akka-http-core" % akkaHttpV,
    "com.typesafe.akka"   %% "akka-http" % akkaHttpV,
    "com.typesafe.akka"   %% "akka-stream" % akkaV,
    "com.typesafe.akka"   %% "akka-remote" % akkaV,
    "net.liftweb"         %% "lift-json" % "2.6",
    "io.herdes.shared"    %% "herdes-commons" % "1.0",
    "io.herdes.shared"    %% "herdes-orient" % "1.0",
    "io.herdes.agent"     %% "herdes-agent-sdk" % "1.0",
    "io.scalaberries"     %% "berries" % "1.0",
    "com.typesafe.akka"   %% "akka-testkit"  % akkaV,
    "com.typesafe.akka"   %% "akka-http-testkit"  % akkaHttpV,
    "org.scalaz"          %% "scalaz-core" % scalazVersion,
    "org.scalaz"          %% "scalaz-effect" % scalazVersion,
    "org.scalaz"          %% "scalaz-typelevel" % scalazVersion,
    "org.scalaz"          %% "scalaz-scalacheck-binding" % scalazVersion % "test",
    "org.scalatest"       %% "scalatest" % "2.2.6" % "test",
    "org.mockito"         % "mockito-core" % "1.10.19"
  )
}