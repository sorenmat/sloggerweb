import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "SloggerWeb"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      //"reactivemongo" % "reactivemongo_2.9.2" % "0.1-SNAPSHOT"
      "org.mongodb" %% "casbah" % "2.4.1"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here
        resolvers += "Novus Release Repository" at "http://repo.novus.com/releases/",
        resolvers += "Novus Snapshots Repository" at "http://repo.novus.com/snapshots/",
        resolvers += "sgodbillon" at "https://bitbucket.org/sgodbillon/repository/raw/master/snapshots/"

  )

}
