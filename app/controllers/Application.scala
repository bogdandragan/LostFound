package controllers

import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.iteratee.{Concurrent, Iteratee}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

class Application extends Controller {

  def index = WebSocket.using[JsValue] { implicit request =>

    val (out,channel) = Concurrent.broadcast[JsValue]

    val in = Iteratee.foreach[JsValue]{
      msg => println(msg);
        channel.push(Json.obj("controller" -> "t38", "status" -> "running"));
    }

    (in, out)
  }
}