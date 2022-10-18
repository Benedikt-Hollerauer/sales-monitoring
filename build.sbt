name := "salesMonitoring"

scalaVersion := "3.2.0"

Compile / scalaSource := baseDirectory.value / "src/main"

Test / scalaSource := baseDirectory.value / "src/test"

// ZIO
libraryDependencies += "dev.zio" %% "zio" % "2.0.2"