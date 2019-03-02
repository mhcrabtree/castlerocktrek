package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.Database

import play.api.libs.json._

case class MedicalMedication(
  regularDetails: Option[String] = None,
  asNeededDetails: Option[String] = None
)

object MedicalMedication {
  val post: Form[MedicalMedication] = Form(
    mapping(
      "regular.details" -> optional(nonEmptyText),
      "asneeded.details" -> optional(nonEmptyText)
    )(MedicalMedication.apply)(MedicalMedication.unapply)
  )
  
}