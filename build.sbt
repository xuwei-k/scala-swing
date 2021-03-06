import ScalaModulePlugin._

scalaModuleSettings

name               := "scala-swing"

version            := "2.0.4-SNAPSHOT"

scalacOptions      ++= Seq("-deprecation", "-feature")

// Map[JvmMajorVersion, List[(ScalaVersion, UseForPublishing)]]
scalaVersionsByJvm in ThisBuild := Map(
   8 -> List("2.11.12", "2.12.6", "2.13.0-M3").map(_ -> true),
   9 -> List("2.11.12", "2.12.6", "2.13.0-M3").map(_ -> false),
  10 -> List("2.11.12", "2.12.6", "2.13.0-M3").map(_ -> false)
)

OsgiKeys.exportPackage := Seq(s"scala.swing.*;version=${version.value}")

mimaPreviousVersion := Some("2.0.0")

// set the prompt (for this build) to include the project id.
shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }

lazy val swing = project.in(file("."))
  .settings(
    libraryDependencies += {
      val v = if (scalaVersion.value == "2.13.0-M3") "3.0.5-M1" else "3.0.5"
      "org.scalatest" %% "scalatest" % v % "test"
    }
  )

lazy val examples = project.in(file("examples"))
  .dependsOn(swing)
  .settings(
    scalaVersion := (scalaVersion in swing).value,
    fork in run := true,
    fork := true
  )

lazy val uitest = project.in(file("uitest"))
  .dependsOn(swing)
  .settings(
    scalaVersion := (scalaVersion in swing).value,
    fork in run := true,
    fork := true
  )
