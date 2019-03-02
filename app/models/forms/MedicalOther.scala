package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.Database

import play.api.libs.json._

case class MedicalOther(
  concernsDetails: Option[String] = None
)

object MedicalOther {
  val post: Form[MedicalOther] = Form(
    mapping(
      "concerns.details" -> optional(nonEmptyText)
    )(MedicalOther.apply)(MedicalOther.unapply)
  )
  
}