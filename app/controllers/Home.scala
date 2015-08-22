package controllers

import play.api.Play
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import play.api.mvc.{Action, Controller}
import slick.driver.{JdbcProfile, JdbcDriver}
import scala.concurrent.ExecutionContext.Implicits.global

class Home extends Controller with HasDatabaseConfig[JdbcProfile]{
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  def index = Action{
    Ok(views.html.index())
  }



}
