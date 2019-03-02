package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.Database

import play.api.libs.json._

case class Emergency(
  name: Name,
  phone: String
)

object Emergency {

  def post(db: Database): Form[Emergency] = Form(
    mapping(
      "name" -> Name.post.mapping,
      "phone" -> nonEmptyText
    )(Emergency.apply)(Emergency.unapply)
  )
  
}