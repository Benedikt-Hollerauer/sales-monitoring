name := "salesMonitoring"

scalaVersion := "3.2.1"

Compile / scalaSource := baseDirectory.value / "src/main"

Test / scalaSource := baseDirectory.value / "src/test"

val zioVersion = "2.0.6"

// ZIO
libraryDependencies += "dev.zio" %% "zio" % zioVersion

// ZIO Test
libraryDependencies ++= Seq(
    "dev.zio" %% "zio-test"          % zioVersion % Test,
    "dev.zio" %% "zio-test-sbt"      % zioVersion % Test,
    "dev.zio" %% "zio-test-magnolia" % zioVersion % Test
)

testFrameworks += new TestFramework(implClassNames = "zio.test.sbt.ZTestFramework")