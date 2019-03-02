package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.Database

import play.api.libs.json._

case class MedicalConsent(
  legalGiven: String
)

object MedicalConsent {
  val post: Form[MedicalConsent] = Form(
    mapping(
      "legal.given" -> nonEmptyText.verifying("error.must.consent", v => v == "yes")
    )(MedicalConsent.apply)(MedicalConsent.unapply)
  )
}