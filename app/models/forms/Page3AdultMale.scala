package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.Database

import play.api.libs.json._

case class Page3AdultMale(
  father: Parent
)

object Page3AdultMale {
  def post(db: Database): Form[Page3AdultMale] = Form(
    mapping(
      "father" -> Parent.post(db).mapping
    )(Page3AdultMale.apply)(Page3AdultMale.unapply)
  )
  
}