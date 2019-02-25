package models.db

import anorm._
import anorm.SqlParser._

case class Child(
  name: Name,
  dob: String,
  gender: String
) {
   
}

object Child {

  val parser: RowParser[Child] = {
    Name.parserChild ~
    get[String]("child_dob") ~
    get[String]("child_gender") map {
      case name ~ dob ~ gender => Child(name, dob, gender)
    }
  }

}