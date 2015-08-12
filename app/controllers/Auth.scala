package controllers

import models.Tables._
import play.api.Play
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import play.api.libs.json._
import play.api.mvc._
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global

class Auth extends Controller with HasDatabaseConfig[JdbcProfile] {
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

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
  def login = Action {

   //insert
   /*val user = new UsersRow(0,"de","dede",Some("e3ed33333333"))
   db.run(Users += user)*/

   //update
   /*val user = new UsersRow(0,"de","dede",Some("e3ed33333333"))
   db.run(Users.filter(_.id === 0).map(_.password).update("ede"))*/

   Ok("")
  }


  def getUsers = Action.async{
    db.run(Users.filter(_.email === "bogdahn@dragan.com.ua").result).map(users => Ok(Json.toJson(users)))
  }


}
