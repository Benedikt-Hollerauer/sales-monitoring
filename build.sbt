name := "salesMonitoring"

scalaVersion := "3.2.1"

Compile / scalaSource := baseDirectory.value / "src/main"

Test / scalaSource := baseDirectory.value / "src/test"

// ZIO
libraryDependencies += "dev.zio" %% "zio" % "2.0.4"

// ZIO Test
libraryDependencies ++= Seq(
    "dev.zio" %% "zio-test"          % "2.0.4" % Test,
    "dev.zio" %% "zio-test-sbt"      % "2.0.4" % Test,
    "dev.zio" %% "zio-test-magnolia" % "2.0.4" % Test
)

testFrameworks += new TestFramework(implClassNames = "zio.test.sbt.ZTestFramework")