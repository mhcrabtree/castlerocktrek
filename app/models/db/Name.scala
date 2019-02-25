package models.db

import anorm._
import anorm.SqlParser._

case class Name(
  first: String,
  middle: Option[String] = None,
  last: String
) {
  def toForm: models.forms.Name = models.forms.Name(this.first, this.middle, this.last) 
}

object Name {

  val parserChild: RowParser[Name] = {
    get[String]("child_name_first") ~
    get[Option[String]]("child_name_middle") ~
    get[String]("child_name_last") map {
      case first ~ middle ~ last => Name(first, middle, last)
    }
  }

}