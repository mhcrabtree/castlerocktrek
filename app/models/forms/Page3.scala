package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.Database

import play.api.libs.json._

case class Page3(
  mother: Parent,
  father: Parent
)

object Page3 {
  def post(db: Database): Form[Page3] = Form(
    mapping(
      "mother" -> Parent.post(db).mapping,
      "father" -> Parent.post(db).mapping
    )(Page3.apply)(Page3.unapply)
  )
  
}