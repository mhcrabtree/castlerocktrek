package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.Database

import play.api.libs.json._

case class Contact(
  phone: String,
  email: String
) {

}

object Contact {
  def post(db: Database): Form[Contact] = Form(
    mapping(
      "phone" -> nonEmptyText, // TODO filter for digits
      "email" -> email
//      "email" -> email.verifying("error.duplicate", v => !User.findByEmail(db, v).isDefined)
    )(Contact.apply)(Contact.unapply)
  )
}