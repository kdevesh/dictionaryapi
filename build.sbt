name := """dictionary-api"""
organization := "com.coding.challenge"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean, LauncherJarPlugin)

scalaVersion := "2.13.0"

libraryDependencies ++= Seq(evolutions, jdbc, guice,
  "com.h2database" % "h2" % "1.4.198",
  "org.glassfish.jaxb" % "jaxb-core" % "2.3.0.1",
  "org.glassfish.jaxb" % "jaxb-runtime" % "2.3.2")
