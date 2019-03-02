package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.Database

import play.api.libs.json._

case class MedicalCondition(
  providerDetails: Option[String] = None,
  epilepsy: String,
  highBloodPressure: String,
  heartDisease: String,
  lungDisease: String,
  orthopedic: String
)

object MedicalCondition{
  val post: Form[MedicalCondition] = Form(
    mapping(
      "provider.details" -> optional(nonEmptyText),
      "epilepsy" -> nonEmptyText,
      "highbloodpressure" -> nonEmptyText,
      "heartdisease" -> nonEmptyText,
      "lungdisease" -> nonEmptyText,
      "orthopedic" -> nonEmptyText
    )(MedicalCondition.apply)(MedicalCondition.unapply)
  )
  
}