package models.db

import play.api.db._

import anorm._
import anorm.SqlParser._

case class Parent(
  name: Name,
  contact: Contact
) {
  lazy val toForm: models.forms.Parent = models.forms.Parent(this.name.toForm, this.contact.toForm) 
}

object Parent {

  val parserMother: RowParser[Parent] = {
    Name.parserMother ~
    Contact.parserMother map {
      case name ~ contact => Parent(name, contact)
    }
  }

  val parserFather: RowParser[Parent] = {
    Name.parserFather ~
    Contact.parserFather map {
      case name ~ contact => Parent(name, contact)
    }
  }

}