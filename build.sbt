lazy val root = project
    .in(file("."))
    .enablePlugins(ScalaJSPlugin)
    .settings(
        name := "sales-monitoring",
        scalaVersion := "3.3.1",
        Compile / scalaSource := baseDirectory.value / "src/main",
        Test / scalaSource := baseDirectory.value / "src/test",
        libraryDependencies ++= Seq(
            zio
        ).flatten,
        testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
    )

val zioVersion = "2.0.19"

val zio = Seq(
    "dev.zio" %% "zio" % zioVersion,
    "dev.zio" %% "zio-test" % zioVersion % Test,
    "dev.zio" %% "zio-test-sbt" % zioVersion % Test,
    "dev.zio" %% "zio-test-magnolia" % zioVersion % Test
)