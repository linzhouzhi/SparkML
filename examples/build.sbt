name := "examples"

version := "1.0"

scalaVersion := "2.10.2"

val projectMainClass = "WordCount"

mainClass in (Compile, packageBin) := Some(projectMainClass)
    