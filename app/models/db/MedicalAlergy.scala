package models.db

import play.api.db._

import anorm._
import anorm.SqlParser._

case class MedicalAlergy(
  medications: String,
  pollen: String,
  toxins: String,
  foods: String,
  orthopedic: String
) {
  lazy val toForm: models.forms.MedicalAlergy = {
    models.forms.MedicalAlergy(this.medications, this.pollen, this.toxins, this.foods, this.orthopedic) 
  }
}

object MedicalAlergy {

  val parser: RowParser[MedicalAlergy] = {
    get[String]("medical_alergies_medications") ~
    get[String]("medical_alergies_pollen") ~
    get[String]("medical_alergies_toxins") ~
    get[String]("medical_alergies_foods") ~
    get[String]("medical_alergies_orthopedic") map {
      case medications ~ pollen ~ toxins ~ foods ~ orthopedic => {
        MedicalAlergy(medications, pollen, toxins, foods, orthopedic)
      }
    }
  }

}