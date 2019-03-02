package models.db

import play.api.db._

import anorm._
import anorm.SqlParser._

case class MedicalConsent(
  legalGiven: String
) {
  lazy val toForm: models.forms.MedicalConsent = {
    models.forms.MedicalConsent(this.legalGiven) 
  }
}

object MedicalConsent {

  val parser: RowParser[MedicalConsent] = {
    get[String]("medical_consent_legal_given") map {
      case legalGiven => {
        MedicalConsent(legalGiven)
      }
    }
  }

}