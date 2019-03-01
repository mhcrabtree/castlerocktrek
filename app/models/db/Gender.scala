package models.db

import play.api._
import play.api.db._

import anorm._
import anorm.SqlParser._

case class Gender (
  id: Int,
  name: String,
  active: Boolean
)

object Gender {
  
  def parser: RowParser[Gender] = {
    get[Int]("id") ~
    get[String]("name") ~
    get[Boolean]("active") map {
      case id ~ name ~ active => Gender(id, name, active)
    }
  }

  def list(db: Database): List[Gender] = db.withConnection { implicit connection =>
    SQL("""
      |SELECT id, name, active
      |FROM gender 
      |ORDER BY name
    """.stripMargin).as(parser.*)
  }
 
  def listActive(db: Database): List[Gender] = this.list(db).filter(f => f.active) 

  def find(db: Database, id: Int): Option[Gender] = db.withConnection { implicit connection =>
    SQL("""
      |SELECT id, name, active
      |FROM gender
      |WHERE id = {id}
    """.stripMargin).on("id" -> id).as(parser.singleOpt)
  }

  def findActive(db: Database, id: Int): Option[Gender] = db.withConnection { implicit connection =>
    this.find(db, id).find(_.active)
  }

  def options(db: Database): List[(String, String)] = {
    this.listActive(db).map(r => r.id.toString -> r.name)
  }
  
}