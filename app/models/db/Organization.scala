package models.db

import play.api._
import play.api.db._

import anorm._
import anorm.SqlParser._

case class Organization(
  id: Int,
  name: String
)

object Organization {
  
  def parser: RowParser[Organization] = {
    get[Int]("id") ~
    get[String]("name") map {
      case id ~ name => Organization(id, name)
    }
  }

  def list(db: Database): List[Organization] = db.withConnection { implicit connection =>
    SQL("""
      |SELECT id, name
      |FROM organization
      |ORDER BY id 
    """.stripMargin).as(parser.*)
  }
  
  def find(db: Database, id: Int): Option[Organization] = db.withConnection { implicit connection =>
    SQL("""
      |SELECT id, name
      |FROM organization
      |WHERE id = {id}
    """.stripMargin).on("id" -> id).as(parser.singleOpt)
  }

  def options(db: Database): List[(String, String)] = {
    this.list(db).map(r => r.id.toString -> r.name)
  }
  
}