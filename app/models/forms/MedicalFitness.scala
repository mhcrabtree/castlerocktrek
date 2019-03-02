package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.Database

import play.api.libs.json._

case class MedicalFitness(
  level: String
)

object MedicalFitness {
  val post: Form[MedicalFitness] = Form(
    mapping(
      "level" -> nonEmptyText
    )(MedicalFitness.apply)(MedicalFitness.unapply)
  )
  
}