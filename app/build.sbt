lazy val root = project
    .in(file("."))
    .enablePlugins(ScalaJSPlugin)
    .settings(
        name := "salesMonitoring",
        scalaVersion := "3.2.2",
        Compile / scalaSource := baseDirectory.value / "src/main",
        Test / scalaSource := baseDirectory.value / "src/test",
        libraryDependencies ++= Seq(
            zio
        ).flatten,
        testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
    )

val zioVersion = "2.0.8"

val zio = Seq(
    "dev.zio" %% "zio" % zioVersion,
    "dev.zio" %% "zio-test" % zioVersion % Test,
    "dev.zio" %% "zio-test-sbt" % zioVersion % Test,
    "dev.zio" %% "zio-test-magnolia" % zioVersion % Test
)