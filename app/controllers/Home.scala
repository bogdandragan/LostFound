package controllers

import java.nio.file._

import org.apache.commons.codec.binary.Base64
import play.api.Play
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import play.api.libs.json.{JsNull, JsValue}
import play.api.mvc._
import slick.driver.JdbcProfile

import scala.concurrent.Future


class Home extends Controller  with HasDatabaseConfig[JdbcProfile]{
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  def index = Action{
      Ok(views.html.index())
    }

  /*def getUsers = Action.async{
    db.run(Users.filter(_.email === "bogdahn@dragan.com.ua").result).map(users => Ok(Json.toJson(users)))
  }*/

  def saveImage = Action.async { implicit request =>
    val json = request.body.asJson.get

    val image = (json \ "image").as[JsValue]
    var base64 = ""
    if(image != JsNull){
      base64 = (image \ "base64").as[String]
      val filetype  = (image \ "filetype").as[String].split('/')(1)
      val filename = System.currentTimeMillis.toString+"."+filetype

      val byteArr : Array[Byte] = Base64.decodeBase64(base64)
      Files.write(Paths.get("images/"+filename), byteArr, StandardOpenOption.CREATE)

    }

    Future.successful(Ok(base64))
  }
}