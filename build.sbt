import NativePackagerHelper._

name := """castlerocktrek"""
organization := "us.btree"
maintainer := "dev@btree.us"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += jdbc
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.46"
libraryDependencies += "com.typesafe.play" %% "anorm" % "2.5.3"
libraryDependencies += "com.typesafe" % "config" % "1.3.3"
//libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.1"
//libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.11.410"
libraryDependencies += "org.webjars" % "bootstrap" % "3.3.4"

scalacOptions ++= Seq("-encoding", "UTF-8", "-Xlint:-unused", "-deprecation", "-unchecked", "-feature")

javaOptions in Universal += "-Djava.net.preferIPv4Stack=true"

EclipseKeys.preTasks := Seq(compile in Compile, compile in Test)

// Adds additional folders to dist
mappings in Universal ++= directory("dist")

// Adds additional packages into Twirl

// Adds additional packages into conf/routes
