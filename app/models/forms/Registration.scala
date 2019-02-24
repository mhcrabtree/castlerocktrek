package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.Database

//import models.db.{ User, Ward }

import play.api.libs.json._

case class Registration(
  name: Name,
  email: String,
  ward: Id 
)

object Registration {
  def post(db: Database): Form[Registration] = Form(
    mapping(
      "name" -> Name.post.mapping,
      "email" -> email,
//      "email" -> email.verifying("error.duplicate", v => !User.findByEmail(db, v).isDefined)
      "ward" -> Ward.id(db).mapping
    )(Registration.apply)(Registration.unapply)
  )
  
}