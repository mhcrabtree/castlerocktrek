package models.db

import play.api.db._

import anorm._
import anorm.SqlParser._

case class MedicalCondition(
  providerDetails: Option[String] = None,
  epilepsy: String,
  highBloodPressure: String,
  heartDisease: String,
  lungDisease: String,
  orthopedic: String
) {
  lazy val toForm: models.forms.MedicalCondition = {
    models.forms.MedicalCondition(this.providerDetails, this.epilepsy, this.highBloodPressure, this.heartDisease, this.lungDisease, this.orthopedic) 
  }
}

object MedicalCondition {

  val parser: RowParser[MedicalCondition] = {
    get[Option[String]]("medical_conditions_provider_details") ~
    get[String]("medical_conditions_epilepsy") ~
    get[String]("medical_conditions_highbloodpressure") ~
    get[String]("medical_conditions_heartdisease") ~
    get[String]("medical_conditions_lungdisease") ~
    get[String]("medical_conditions_orthopedic") map {
      case providerDetails ~ epilepsy ~ highBloodPressure ~ heartDisease ~ lungDisease ~ orthopedic => {
        MedicalCondition(providerDetails, epilepsy, highBloodPressure, heartDisease, lungDisease, orthopedic)
      }
    }
  }

}