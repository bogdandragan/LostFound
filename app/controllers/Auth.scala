package controllers

import play.api.mvc._
import models.Tables._
import slick.driver.MySQLDriver.api._
import dbConfig.driver.api._
import slick.dbio.DBIO

object Auth extends Controller{

  def login = Action {
    Users.map(c=>(c.id, c.email, c.password, c.sessionId)).forceInsert(0,"dede","dedecccrrw",Some("786829729"))

    Ok("ok")
  }

}
