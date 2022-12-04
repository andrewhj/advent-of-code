ThisBuild / scalaVersion := "3.2.1"
ThisBuild / version := "1.0.0"
ThisBuild / organization := "com.example"

lazy val root = (project in file("."))
  .aggregate(common, advent2022)
lazy val common = project in file("./common")
lazy val advent2022 = (project in file("./advent2022"))
  .settings(libraryDependencies ++= Seq(
    "org.scalactic" %% "scalactic" % "3.2.14",
    "org.scalatest" %% "scalatest" % "3.2.14" % "test"
  ))
  .dependsOn(common)
