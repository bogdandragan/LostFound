package controllers

import models.Tables._
import play.api.Play
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import play.api.libs.json.Json
import play.api.mvc._
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class Account extends Controller  with HasDatabaseConfig[JdbcProfile]{
   val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    def signin = Action{
      Ok(views.html.index())
    }


 /* def login = Action.async { implicit rs =>
    //val action: DBIO[Seq[String]] = Users.map(_.email).result
    //val res: Future[Seq[String]] = db.run(action)
    // val i = Users.map(c=>(c.id, c.email, c.password, c.sessionId)).result
    //val json = Json.toJson(res)
    //res.map{r => Ok(json)}
    val resultingUsers = db.run(Users.filter(_.email === "bogdahn@dragan.com.ua").result)

    if(resultingUsers == null){
      resultingUsers.map{
        users => Ok(Json.toJson(users))
      }
    }
    else{
      resultingUsers.map{
        users => Ok(Json.toJson(users))
      }
    }

  }*/
  def doSignin() = Action.async { implicit request =>

   request.session.get("id").map { user =>
       Future.successful(Ok("Hello " + user))
     }.getOrElse {
      Future.successful(Unauthorized("Oops, you are not connected"))

     }

   val json = request.body.asJson.get

   val action= (json \ "action").as[String]

   if(action != "login"){
     //val futureInt = Future { BadRequest }
     Future.successful(BadRequest)
    // futureInt.map(i => Ok("Got result: " + i))
   }
   else{
     val params = json \ "params"
     val email = (params \ "email").as[String]
     val password = (params \ "password").as[String]

     val q = db.run(Users.filter(f=> f.email === email && f.password === password).result).map(users => users)

      q.map{
        u =>
          if(u.nonEmpty) {
            Ok(Json.obj("error"->"")).withSession("id" -> "45678913465798")
          }
          else{
            Ok(Json.obj("error"->"User not found"))
          }
      }
       //Future.successful(Ok(q.))
   }
  // BadRequest("invalid requeuuuuust")
   //insert
   /*val user = new UsersRow(0,"de","dede",Some("e3ed33333333"))
   db.run(Users += user)*/

   //update
   /*val user = new UsersRow(0,"de","dede",Some("e3ed33333333"))
   db.run(Users.filter(_.id === 0).map(_.password).update("ede"))*/

   //select
   //db.run(Users.result).map
   //val q = Users.map(u=>u.email)


  }


  /*def getUsers = Action.async{
    db.run(Users.filter(_.email === "bogdahn@dragan.com.ua").result).map(users => Ok(Json.toJson(users)))
  }*/


}
