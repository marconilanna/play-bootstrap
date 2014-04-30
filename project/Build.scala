import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {
	val appName    = "AppName"
	val appVersion = "1.0"

	val appDependencies = Seq(
		  jdbc
		, anorm
		, "mysql"                          %  "mysql-connector-java"        % "5.1.30"
		, "com.ibm.icu"                    %  "icu4j"                       % "53.1"
		, "org.scalatest"                  %% "scalatest"                   % "1.9.1"    % "test"
		, "org.seleniumhq.selenium"        %  "selenium-java"               % "2.32.0"   % "test"
		)

	val main = play.Project(appName, appVersion, appDependencies).settings(
		  scalacOptions ++= Seq(
			  "-deprecation"
			, "-feature"
			, "-unchecked"
			, "-Xfatal-warnings"
			, "-Xlint"
			, "-Yno-adapted-args"
			, "-Ywarn-all"
			, "-Ywarn-dead-code"
			)
		, templatesImport ++= Seq("helpers._", "tags._", "util._")
		, testOptions in Test := Nil // Disables built-in specs2, it conflicts with ScalaTest
		)
}
