package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.Database

import play.api.libs.json._

case class Page1(
  organization: Id,
  ward: Id 
)

object Page1 {
  def post(db: Database): Form[Page1] = Form(
    mapping(
      "organization" -> Organization.id(db).mapping,
      "ward" -> Ward.id(db).mapping
    )(Page1.apply)(Page1.unapply)
  )
  
}