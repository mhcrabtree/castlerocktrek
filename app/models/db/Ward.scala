package models.db

import play.api._
import play.api.db._

import anorm._
import anorm.SqlParser._

case class Ward(
	id: Int,
	name: String
)

object Ward {
	
	def parser: RowParser[Ward] = {
		get[Int]("id") ~
		get[String]("name") map {
			case id ~ name => Ward(id, name)
		}
	}

	def list(db: Database): List[Ward] = db.withConnection { implicit connection =>
		SQL("""
			|SELECT id, name
			|FROM ward
      |ORDER BY name
		""".stripMargin).as(parser.*)
	}
	
	def find(db: Database, id: Int): Option[Ward] = db.withConnection { implicit connection =>
		SQL("""
			|SELECT id, name
			|FROM ward
      |WHERE id = {id}
		""".stripMargin).on("id" -> id).as(parser.singleOpt)
	}

	def options(db: Database): List[(String, String)] = {
		this.list(db).map(r => r.id.toString -> r.name)
	}
	
}