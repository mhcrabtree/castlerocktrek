package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.Database

import play.api.libs.json._

case class Page4(
  emergency: Emergency
)

object Page4 {
  def post(db: Database): Form[Page4] = Form(
    mapping(
      "emergency" -> Emergency.post(db).mapping
    )(Page4.apply)(Page4.unapply)
  )
  
}