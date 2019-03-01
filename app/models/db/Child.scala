package models.db

import play.api.db._

import anorm._
import anorm.SqlParser._

case class Child(
  name: Name,
  dob: String,
  gender: Gender 
) {
   
}

object Child {

  def parser(db: Database): RowParser[Child] = {
    Name.parserChild ~
    get[String]("child_dob") ~
    get[Int]("child_gender_id") map {
      case name ~ dob ~ genderId => {
        val gender = Gender.find(db, genderId).get // save because we have constraints enabled in the db
        Child(name, dob, gender)
      }
    }
  }

}