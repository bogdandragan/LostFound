package models

import play.api.libs.json.Json

// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.MySQLDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema = Users.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Users
    *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
    *  @param email Database column email SqlType(VARCHAR), Length(50,true)
    *  @param password Database column password SqlType(VARCHAR), Length(50,true)
    *  @param sessionId Database column session_id SqlType(VARCHAR), Length(500,true), Default(None) */
  case class UsersRow(id: Int, email: String, password: String, sessionId: Option[String] = None)

    //JSON read/write
    implicit val userFormat = Json.writes[UsersRow]

//  implicit val userWrites = Json.writes[UsersRow]
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]]): GR[UsersRow] = GR{
    prs => import prs._
      UsersRow.tupled((<<[Int], <<[String], <<[String], <<?[String]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends Table[UsersRow](_tableTag, "users") {
    def * = (id, email, password, sessionId) <> (UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(email), Rep.Some(password), sessionId).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get, _3.get, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column email SqlType(VARCHAR), Length(50,true) */
    val email: Rep[String] = column[String]("email", O.Length(50,varying=true))
    /** Database column password SqlType(VARCHAR), Length(50,true) */
    val password: Rep[String] = column[String]("password", O.Length(50,varying=true))
    /** Database column session_id SqlType(VARCHAR), Length(500,true), Default(None) */
    val sessionId: Rep[Option[String]] = column[Option[String]]("session_id", O.Length(500,varying=true), O.Default(None))

    def byId(id:Rep[Int]) = Users.filter(_.id === id)
    def findById = Compiled(byId _)
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))





}