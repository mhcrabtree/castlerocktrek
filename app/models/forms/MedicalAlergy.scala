package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.Database

import play.api.libs.json._

case class MedicalAlergy(
  medications: String,
  pollen: String,
  toxins: String,
  foods: String,
  orthopedic: String
)

object MedicalAlergy {
  val post: Form[MedicalAlergy] = Form(
    mapping(
      "medications" -> nonEmptyText,
      "pollen" -> nonEmptyText,
      "toxins" -> nonEmptyText,
      "foods" -> nonEmptyText,
      "orthopedic" -> nonEmptyText
    )(MedicalAlergy.apply)(MedicalAlergy.unapply)
  )
  
}