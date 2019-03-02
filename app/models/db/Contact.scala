package models.db

import play.api.db._

import anorm._
import anorm.SqlParser._

case class Contact(
  phone: String,
  email: String
) {
  lazy val toForm: models.forms.Contact = models.forms.Contact(this.phone, this.email) 
}

object Contact {

  val parser: RowParser[Contact] = {
    get[String]("parent_mother_contact_phone") ~
    get[String]("parent_mother_contact_email") map {
      case phone ~ email => Contact(phone, email)
    }
  }

}