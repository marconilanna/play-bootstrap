import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {
	val appName    = "AppName"
	val appVersion = "1.0"

	val appDependencies = Seq(
		  jdbc
		, anorm
		, "org.scalatest"                  %% "scalatest"                   % "1.9.1"    % "test"
		)

	val main = play.Project(appName, appVersion, appDependencies).settings(
		  scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-Xlint")
		, testOptions in Test := Nil // Disables built-in specs2, it conflicts with ScalaTest
		)
}
