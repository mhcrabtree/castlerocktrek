package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.Database

import play.api.libs.json._

case class Page6(
  condition: MedicalCondition,
  medication: MedicalMedication,
  alergy: MedicalAlergy,
  hospitalization: MedicalHospitalization,
  mental: MedicalMental,
  fitness: MedicalFitness,
  other: MedicalOther,
  consent: MedicalConsent
)

object Page6 {
  val post: Form[Page6] = Form(
    mapping(
      "medical.conditions" -> MedicalCondition.post.mapping,
      "medical.medications" -> MedicalMedication.post.mapping,
      "medical.alergies" -> MedicalAlergy.post.mapping,
      "medical.hospitalization" -> MedicalHospitalization.post.mapping,
      "medical.mental" -> MedicalMental.post.mapping,
      "medical.fitness" -> MedicalFitness.post.mapping,
      "medical.other" -> MedicalOther.post.mapping,
      "medical.consent" -> MedicalConsent.post.mapping
    )(Page6.apply)(Page6.unapply)
  )
}