

object MySQLPlayground {
  def main(args: Array[String]) {
    slick.codegen.SourceCodeGenerator.main(
      Array("slick.driver.MySQLDriver", "com.mysql.jdbc.Driver",
        "jdbc:mysql://localhost/lostfound", "test/models", "Gen", "root", "123"))
  }
}