package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.Database

import play.api.libs.json._

case class Page3AdultFemale(
  mother: Parent
)

object Page3AdultFemale {
  def post(db: Database): Form[Page3AdultFemale] = Form(
    mapping(
      "mother" -> Parent.post(db).mapping
    )(Page3AdultFemale.apply)(Page3AdultFemale.unapply)
  )
  
}