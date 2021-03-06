package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.Database

import play.api.libs.json._

case class Page2(
  name: Name,
  dob: String,
  gender: Id
)

object Page2 {
  def post(db: Database): Form[Page2] = Form(
    mapping(
      "name" -> Name.post.mapping,
      "dob" -> nonEmptyText.verifying("error.invalid", v => true), // TODO parse the dob and validate it
      "gender" -> Gender.id(db).mapping
    )(Page2.apply)(Page2.unapply)
  )
  
}