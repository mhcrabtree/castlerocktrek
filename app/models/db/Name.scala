package models.db

import anorm._
import anorm.SqlParser._

case class Name(
  first: String,
  middle: Option[String] = None,
  last: String
) {
  val toForm: models.forms.Name = models.forms.Name(this.first, this.middle, this.last) 

  val full: String = (this.first, this.middle, this.last) match {
    case (f, None, l) => "%s %s".format(f, l)
    case (f, Some(m), l) => "%s %s %s".format(f, m, l)
  }
}

object Name {

  val parserChild: RowParser[Name] = {
    get[String]("child_name_first") ~
    get[Option[String]]("child_name_middle") ~
    get[String]("child_name_last") map {
      case first ~ middle ~ last => Name(first, middle, last)
    }
  }

  val parserMother: RowParser[Name] = {
    get[String]("parent_mother_name_first") ~
    get[String]("parent_mother_name_last") map {
      case first ~ last => Name(first, None, last)
    }
  }

  val parserFather: RowParser[Name] = {
    get[String]("parent_father_name_first") ~
    get[String]("parent_father_name_last") map {
      case first ~ last => Name(first, None, last)
    }
  }

  val parserEmergency: RowParser[Name] = {
    get[String]("emergency_name_first") ~
    get[String]("emergency_name_last") map {
      case first ~ last => Name(first, None, last)
    }
  }

}