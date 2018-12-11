name := "collections-benchmark"
scalaVersion := "2.12.7"
crossScalaVersions := Seq("2.11.11", "2.12.7")

enablePlugins(JmhPlugin)

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0"

