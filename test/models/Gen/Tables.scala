package Gen
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.MySQLDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema = Array(Categories.schema, Cities.schema, Found.schema, Lost.schema, Regions.schema, Users.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Categories
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(100,true) */
  case class CategoriesRow(id: Int, name: String)
  /** GetResult implicit for fetching CategoriesRow objects using plain SQL queries */
  implicit def GetResultCategoriesRow(implicit e0: GR[Int], e1: GR[String]): GR[CategoriesRow] = GR{
    prs => import prs._
    CategoriesRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table categories. Objects of this class serve as prototypes for rows in queries. */
  class Categories(_tableTag: Tag) extends Table[CategoriesRow](_tableTag, "categories") {
    def * = (id, name) <> (CategoriesRow.tupled, CategoriesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name)).shaped.<>({r=>import r._; _1.map(_=> CategoriesRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(100,true) */
    val name: Rep[String] = column[String]("name", O.Length(100,varying=true))
  }
  /** Collection-like TableQuery object for table Categories */
  lazy val Categories = new TableQuery(tag => new Categories(tag))

  /** Entity class storing rows of table Cities
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param regionId Database column region_id SqlType(INT)
   *  @param name Database column name SqlType(VARCHAR), Length(50,true)
   *  @param phoneCode Database column phone_code SqlType(VARCHAR), Length(7,true), Default(None) */
  case class CitiesRow(id: Int, regionId: Int, name: String, phoneCode: Option[String] = None)
  /** GetResult implicit for fetching CitiesRow objects using plain SQL queries */
  implicit def GetResultCitiesRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]]): GR[CitiesRow] = GR{
    prs => import prs._
    CitiesRow.tupled((<<[Int], <<[Int], <<[String], <<?[String]))
  }
  /** Table description of table cities. Objects of this class serve as prototypes for rows in queries. */
  class Cities(_tableTag: Tag) extends Table[CitiesRow](_tableTag, "cities") {
    def * = (id, regionId, name, phoneCode) <> (CitiesRow.tupled, CitiesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(regionId), Rep.Some(name), phoneCode).shaped.<>({r=>import r._; _1.map(_=> CitiesRow.tupled((_1.get, _2.get, _3.get, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column region_id SqlType(INT) */
    val regionId: Rep[Int] = column[Int]("region_id")
    /** Database column name SqlType(VARCHAR), Length(50,true) */
    val name: Rep[String] = column[String]("name", O.Length(50,varying=true))
    /** Database column phone_code SqlType(VARCHAR), Length(7,true), Default(None) */
    val phoneCode: Rep[Option[String]] = column[Option[String]]("phone_code", O.Length(7,varying=true), O.Default(None))

    /** Index over (name) (database name name) */
    val index1 = index("name", name)
  }
  /** Collection-like TableQuery object for table Cities */
  lazy val Cities = new TableQuery(tag => new Cities(tag))

  /** Entity class storing rows of table Found
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param title Database column title SqlType(VARCHAR), Length(80,true)
   *  @param categoryId Database column category_id SqlType(INT)
   *  @param description Database column description SqlType(VARCHAR), Length(1000,true)
   *  @param cityId Database column city_id SqlType(INT)
   *  @param contact Database column contact SqlType(VARCHAR), Length(50,true)
   *  @param email Database column email SqlType(VARCHAR), Length(50,true) */
  case class FoundRow(id: Int, title: String, categoryId: Int, description: String, cityId: Int, contact: String, email: String)
  /** GetResult implicit for fetching FoundRow objects using plain SQL queries */
  implicit def GetResultFoundRow(implicit e0: GR[Int], e1: GR[String]): GR[FoundRow] = GR{
    prs => import prs._
    FoundRow.tupled((<<[Int], <<[String], <<[Int], <<[String], <<[Int], <<[String], <<[String]))
  }
  /** Table description of table found. Objects of this class serve as prototypes for rows in queries. */
  class Found(_tableTag: Tag) extends Table[FoundRow](_tableTag, "found") {
    def * = (id, title, categoryId, description, cityId, contact, email) <> (FoundRow.tupled, FoundRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(title), Rep.Some(categoryId), Rep.Some(description), Rep.Some(cityId), Rep.Some(contact), Rep.Some(email)).shaped.<>({r=>import r._; _1.map(_=> FoundRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column title SqlType(VARCHAR), Length(80,true) */
    val title: Rep[String] = column[String]("title", O.Length(80,varying=true))
    /** Database column category_id SqlType(INT) */
    val categoryId: Rep[Int] = column[Int]("category_id")
    /** Database column description SqlType(VARCHAR), Length(1000,true) */
    val description: Rep[String] = column[String]("description", O.Length(1000,varying=true))
    /** Database column city_id SqlType(INT) */
    val cityId: Rep[Int] = column[Int]("city_id")
    /** Database column contact SqlType(VARCHAR), Length(50,true) */
    val contact: Rep[String] = column[String]("contact", O.Length(50,varying=true))
    /** Database column email SqlType(VARCHAR), Length(50,true) */
    val email: Rep[String] = column[String]("email", O.Length(50,varying=true))
  }
  /** Collection-like TableQuery object for table Found */
  lazy val Found = new TableQuery(tag => new Found(tag))

  /** Entity class storing rows of table Lost
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param title Database column title SqlType(VARCHAR), Length(80,true)
   *  @param categoryId Database column category_id SqlType(INT)
   *  @param description Database column description SqlType(VARCHAR), Length(1000,true)
   *  @param cityId Database column city_id SqlType(INT)
   *  @param contact Database column contact SqlType(VARCHAR), Length(50,true)
   *  @param email Database column email SqlType(VARCHAR), Length(50,true) */
  case class LostRow(id: Int, title: String, categoryId: Int, description: String, cityId: Int, contact: String, email: String)
  /** GetResult implicit for fetching LostRow objects using plain SQL queries */
  implicit def GetResultLostRow(implicit e0: GR[Int], e1: GR[String]): GR[LostRow] = GR{
    prs => import prs._
    LostRow.tupled((<<[Int], <<[String], <<[Int], <<[String], <<[Int], <<[String], <<[String]))
  }
  /** Table description of table lost. Objects of this class serve as prototypes for rows in queries. */
  class Lost(_tableTag: Tag) extends Table[LostRow](_tableTag, "lost") {
    def * = (id, title, categoryId, description, cityId, contact, email) <> (LostRow.tupled, LostRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(title), Rep.Some(categoryId), Rep.Some(description), Rep.Some(cityId), Rep.Some(contact), Rep.Some(email)).shaped.<>({r=>import r._; _1.map(_=> LostRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column title SqlType(VARCHAR), Length(80,true) */
    val title: Rep[String] = column[String]("title", O.Length(80,varying=true))
    /** Database column category_id SqlType(INT) */
    val categoryId: Rep[Int] = column[Int]("category_id")
    /** Database column description SqlType(VARCHAR), Length(1000,true) */
    val description: Rep[String] = column[String]("description", O.Length(1000,varying=true))
    /** Database column city_id SqlType(INT) */
    val cityId: Rep[Int] = column[Int]("city_id")
    /** Database column contact SqlType(VARCHAR), Length(50,true) */
    val contact: Rep[String] = column[String]("contact", O.Length(50,varying=true))
    /** Database column email SqlType(VARCHAR), Length(50,true) */
    val email: Rep[String] = column[String]("email", O.Length(50,varying=true))
  }
  /** Collection-like TableQuery object for table Lost */
  lazy val Lost = new TableQuery(tag => new Lost(tag))

  /** Entity class storing rows of table Regions
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(50,true) */
  case class RegionsRow(id: Int, name: String)
  /** GetResult implicit for fetching RegionsRow objects using plain SQL queries */
  implicit def GetResultRegionsRow(implicit e0: GR[Int], e1: GR[String]): GR[RegionsRow] = GR{
    prs => import prs._
    RegionsRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table regions. Objects of this class serve as prototypes for rows in queries. */
  class Regions(_tableTag: Tag) extends Table[RegionsRow](_tableTag, "regions") {
    def * = (id, name) <> (RegionsRow.tupled, RegionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name)).shaped.<>({r=>import r._; _1.map(_=> RegionsRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(50,true) */
    val name: Rep[String] = column[String]("name", O.Length(50,varying=true))
  }
  /** Collection-like TableQuery object for table Regions */
  lazy val Regions = new TableQuery(tag => new Regions(tag))

  /** Entity class storing rows of table Users
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param email Database column email SqlType(VARCHAR), Length(50,true)
   *  @param password Database column password SqlType(VARCHAR), Length(255,true)
   *  @param sessionId Database column session_id SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param newUser Database column new_user SqlType(TINYINT)
   *  @param newPassword Database column new_password SqlType(VARCHAR), Length(255,true), Default(None) */
  case class UsersRow(id: Int, email: String, password: String, sessionId: Option[String] = None, newUser: Byte, newPassword: Option[String] = None)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[Byte]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[Int], <<[String], <<[String], <<?[String], <<[Byte], <<?[String]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends Table[UsersRow](_tableTag, "users") {
    def * = (id, email, password, sessionId, newUser, newPassword) <> (UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(email), Rep.Some(password), sessionId, Rep.Some(newUser), newPassword).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get, _3.get, _4, _5.get, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column email SqlType(VARCHAR), Length(50,true) */
    val email: Rep[String] = column[String]("email", O.Length(50,varying=true))
    /** Database column password SqlType(VARCHAR), Length(255,true) */
    val password: Rep[String] = column[String]("password", O.Length(255,varying=true))
    /** Database column session_id SqlType(VARCHAR), Length(255,true), Default(None) */
    val sessionId: Rep[Option[String]] = column[Option[String]]("session_id", O.Length(255,varying=true), O.Default(None))
    /** Database column new_user SqlType(TINYINT) */
    val newUser: Rep[Byte] = column[Byte]("new_user")
    /** Database column new_password SqlType(VARCHAR), Length(255,true), Default(None) */
    val newPassword: Rep[Option[String]] = column[Option[String]]("new_password", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
