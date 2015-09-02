package controllers

import java.sql.SQLTimeoutException
import javax.inject.Inject

import models.Tables._
import org.mindrot.jbcrypt.BCrypt
import play.api.Play
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import play.api.libs.json.Json
import play.api.libs.mailer._
import play.api.mvc._
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Account @Inject()(mailer: MailerClient) extends Controller  with HasDatabaseConfig[JdbcProfile] {
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  def index = Action { implicit request =>
    request.session.get("id").map { user =>
      Ok(views.html.account.account())
    }.getOrElse {
      Redirect(routes.Account.signin())
    }
  }

  def signin = Action { implicit request =>
    request.session.get("id").map { user =>
      Redirect(routes.Account.index())
    }.getOrElse {
      Ok(views.html.account.signin())
    }
  }

  def register = Action {
    Ok(views.html.account.register())
  }

  def forgot = Action {
    Ok(views.html.account.forgot())
  }

  def newPassword = Action {
    Ok(views.html.account.newpassword())
  }

  def checkSession = Action { implicit request =>
    request.session.get("id").map { user =>
      Ok(Json.obj("error" -> ""))
    }.getOrElse {
      Ok(Json.obj("error" -> "invalid"))
    }
  }

  def logout = Action { implicit request =>
    request.session.get("id").map { user =>
      Ok("Bye").withNewSession
    }.getOrElse {
      Ok("")
    }
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
    val json = request.body.asJson.get

    val action = (json \ "action").as[String]

    if (action != "login") {
      //val futureInt = Future { BadRequest }
      Future.successful(BadRequest)
      // futureInt.map(i => Ok("Got result: " + i))
    }
    else {
      val params = json \ "params"
      val email = (params \ "email").as[String]
      val password = (params \ "password").as[String]

      val q = db.run(Users.filter(f => f.email === email && f.password === password && f.newUser === 0.toByte).result).map(users => users)

      q.map {
        u =>
          if (u.nonEmpty) {
            Ok(Json.obj("error" -> "")).withSession("id" -> BCrypt.hashpw(System.currentTimeMillis.toString, BCrypt.gensalt()))
          }
          else {
            Ok(Json.obj("error" -> "User not found"))
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

  def doRegister() = Action.async { implicit request =>

    val json = request.body.asJson.get

    val action = (json \ "action").as[String]
    if(action != "register"){
      Future.successful(BadRequest)
    }
    else{
      val params = json \ "params"
      val email = (params \ "email").as[String]
      val password = (params \ "password").as[String]

      //check if user exists
      val q = db.run(Users.filter(f => f.email === email).result).map(users => users)

      q.map {
        u =>
          if (u.nonEmpty) {
            Ok(Json.obj("error" -> "Email exists"))
          }
          else {
            //generate password hash
            val passwordHash : String = BCrypt.hashpw(password, BCrypt.gensalt())
            //insert user
            val user = new UsersRow(0,email,passwordHash,None,1)
            db.run(Users += user)
            //send email
            sendRegConfirmation(email, passwordHash)
            Ok(Json.obj("error" -> ""))
          }
      }
    }
  }

  def sendRegConfirmation(sendTo: String, hash: String){
    val email = Email(
      "Confirm registration email",
      "Unreply <from@email.com>",
      Seq("Miss TO <"+sendTo+">"),
      bodyText = Some("A text message"),
      bodyHtml = Some("<html><body><p>"+play.Play.application().configuration().getString("application.baseUrl")+"account/register/confirm?email="+sendTo+"&hash="+hash+"</p></body></html>")
    )
    val id = mailer.send(email)
  }

  def sendForgotEmail(sendTo: String, hash: String){
    val email = Email(
      "Restore password email",
      "Unreply <from@email.com>",
      Seq("Miss TO <"+sendTo+">"),
      bodyText = Some("A text message"),
      bodyHtml = Some("<html><body><p>"+play.Play.application().configuration().getString("application.baseUrl")+"account/signin/newpassword?email="+sendTo+"&hash="+hash+"</p></body></html>")
    )
    val id = mailer.send(email)
  }

  def regConfirm = Action {
    Ok(views.html.account.regconfirm())
  }

  def checkRegistration = Action.async { implicit request =>
    val json = request.body.asJson.get

    val action = (json \ "action").as[String]

    if(action != "checkReg"){
      Future.successful(BadRequest)
    }
    else{
      val params = json \ "params"
      val email = (params \ "email").as[String]
      val hash = (params \ "hash").as[String]

      val q = db.run(Users.filter(f=> f.email === email && f.password === hash).map(_.newUser).update(0))

      q.map(
        u =>
          if(u > 0){
            Ok(Json.obj("error" -> ""))
          }
          else{
            Ok(Json.obj("error" -> "User not found"))
          }
      )
    }
  }

  def doForgot = Action.async{ implicit request =>

    val json = request.body.asJson.get

    val action = (json \ "action").as[String]
    if(action != "forgot"){
      Future.successful(BadRequest)
    }
    else{
      val params = json \ "params"
      val email = (params \ "email").as[String]

      val q = db.run(Users.filter(_.email === email).result).map(users=>users)

      q.map(
        u=>
          if(u.nonEmpty){
            val hash : String = BCrypt.hashpw(email, BCrypt.gensalt())
            db.run(Users.filter(f=> f.email === email).map(_.newPassword).update(Option(hash)))
            sendForgotEmail(email, hash)
            Ok(Json.obj("error" -> ""))
          }
          else{
            Ok(Json.obj("error" -> "email not found"))
          }
      )
    }
  }

  def doNewPassword = Action.async{ implicit request =>

    val json = request.body.asJson.get

    val action = (json \ "action").as[String]
    if(action != "newpassword"){
      Future.successful(BadRequest)
    }
    else{
      val params = json \ "params"
      val email = (params \ "email").as[String]
      val hash = (params \ "hash").as[String]
      val newPassword = (params \ "newpassword").as[String]

      val newPasswordHash : String = BCrypt.hashpw(newPassword, BCrypt.gensalt())

      val q = db.run(Users.filter(f=> f.email === email && f.newPassword === hash).map(m=>(m.password,m.newPassword)).update(newPasswordHash,Option("")))

      q.map(
        u =>
          if(u > 0){
            Ok(Json.obj("error" -> ""))
          }
          else{
            Ok(Json.obj("error" -> "User not found, password not updated"))
          }
      )
    }
  }

}