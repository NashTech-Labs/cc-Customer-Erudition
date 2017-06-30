name := """customer_erudition_1"""
organization := "com.knoldus"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.2"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0" % Test
libraryDependencies ++= Seq(
  cache,
  ws,
  "com.h2database"    % 	   "h2"                    %   "1.4.187" ,
  "org.postgresql"   %      "postgresql" % "9.4-1206-jdbc4",
  specs2 % Test
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.knoldus.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.knoldus.binders._"
