package controllers

import java.sql.SQLTimeoutException
import javax.inject.Inject

import models.Tables._
import org.mindrot.jbcrypt.BCrypt
import play.api.Play
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import play.api.libs.json.{JsValue, Writes, Json}
import play.api.libs.mailer._
import play.api.mvc._
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Announcement @Inject()(mailer: MailerClient) extends Controller  with HasDatabaseConfig[JdbcProfile] {
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  def newAnnouncement = Action {
    Ok(views.html.announcement.newannouncement())
  }

  implicit val addressWrites = new Writes[(String, String)] {
    def writes(t: (String,String)) =
      Json.obj("formatted_address" -> Json.toJsFieldJsValueWrapper(t._1+", "+t._2))
  }
  def getAddress = Action.async { implicit request =>
    val param = request.getQueryString("q").getOrElse("")

    val q = db.run(Cities.filter(_.name.like('%'+param.toString+'%')).join(Regions).on(_.regionId === _.id).map{case(c, r) =>(c.name, r.name)}.result)

    q.map(
      res =>
        if(res.nonEmpty)
        Ok(Json.toJson(res))
    else
          Ok(Json.obj(param.toString -> param))
    )


   // Future.successful(Ok(Json.obj("city"->Json.arr(Json.obj("formated" -> "Alabama"),Json.obj("formated"->"Bababa")))))
  }

  def getCategories = Action.async { implicit request =>
    var q = db.run(Categories.map(m=>m.name).result)

    q.map(
      res =>
        Ok(Json.toJson(res))
    )
   // Future.successful(Ok(Json.toJson(q)))
  }
}

