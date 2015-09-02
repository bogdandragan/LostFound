name := "LostFound"

version := "1.0"

lazy val `lostfound` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  "com.typesafe.play" %% "play-slick" % "1.0.1",
  "com.typesafe.slick" %% "slick-codegen" % "3.0.1",
  "com.typesafe.slick" %% "slick" % "3.0.1",
  "mysql" % "mysql-connector-java" % "5.1.12",
  "org.mindrot" % "jbcrypt" % "0.3m",
  "org.apache.commons" % "commons-email" % "1.3.3",
  "com.typesafe.play" %% "play-mailer" % "3.0.1"


)

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator