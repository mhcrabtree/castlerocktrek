package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.Database

import play.api.libs.json._

case class Parent(
  name: Name,
  contact: Contact
)

object Parent {
  def post(db: Database): Form[Parent] = Form(
    mapping(
      "name" -> Name.post.mapping,
      "contact" -> Contact.post(db).mapping
    )(Parent.apply)(Parent.unapply)
  )
  
}