package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.Database

import play.api.libs.json._

case class MedicalHospitalization(
  fiveYears: String,
  fiveYearsDetails: Option[String] = None
)

object MedicalHospitalization {
  val post: Form[MedicalHospitalization] = Form(
    mapping(
      "5years" -> nonEmptyText,
      "5years.details" -> optional(nonEmptyText)
    )(MedicalHospitalization.apply)(MedicalHospitalization.unapply)
  )
  
}