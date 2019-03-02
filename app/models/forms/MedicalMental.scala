package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.Database

import play.api.libs.json._

case class MedicalMental(
  concerns: String,
  concernsDetails: Option[String] = None
)

object MedicalMental {
  val post: Form[MedicalMental] = Form(
    mapping(
      "concerns" -> nonEmptyText,
      "concerns.details" -> optional(nonEmptyText) 
    )(MedicalMental.apply)(MedicalMental.unapply)
  )
}