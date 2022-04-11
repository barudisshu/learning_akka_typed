lazy val log4j2V     = "2.17.1"
lazy val akkaV       = "2.6.19"
lazy val circeV      = "0.14.1"
lazy val scalatestV  = "3.2.11"
lazy val scalacheckV = "3.2.11.0"

lazy val root = project
  .in(file("."))
  .settings(
    name         := """learning_akka_typed""",
    version      := "0.1.0",
    scalaVersion := "3.1.2",
    organization := "info.galudisu",
    homepage     := Some(url("https://github.com/barudisshu/learning_akka_typed")),
    licenses     := List("MIT" -> url("https://opensource.org/licenses/MIT")),
    developers := List(
      Developer(
        "barudisshu",
        "Galudisu",
        "galudisu@gmail.com",
        url("https://galudisu.info")
      )
    ),
    libraryDependencies ++= Seq(
      // log4j2
      "org.apache.logging.log4j" % "log4j-api"        % log4j2V,
      "org.apache.logging.log4j" % "log4j-core"       % log4j2V,
      "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4j2V,
      // Akka
      "com.typesafe.akka" %% "akka-actor-typed"            % akkaV,
      "com.typesafe.akka" %% "akka-slf4j"                  % akkaV,
      "com.typesafe.akka" %% "akka-actor-testkit-typed"    % akkaV % Test,
      "com.typesafe.akka" %% "akka-stream"                 % akkaV,
      "com.typesafe.akka" %% "akka-stream-testkit"         % akkaV % Test,
      "com.typesafe.akka" %% "akka-cluster-typed"          % akkaV,
      "com.typesafe.akka" %% "akka-cluster-sharding-typed" % akkaV,
      "com.typesafe.akka" %% "akka-serialization-jackson"  % akkaV,
      "io.circe"          %% "circe-generic"               % circeV,
      // test
      "org.scalatest"     %% "scalatest"       % scalatestV  % Test,
      "org.scalatestplus" %% "scalacheck-1-15" % scalacheckV % Test
    )
  )
