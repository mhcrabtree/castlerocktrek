package models.db

import play.api.db._

import anorm._
import anorm.SqlParser._

case class MedicalHospitalization(
  fiveYears: String,
  fiveYearsDetails: Option[String] = None
) {
  lazy val toForm: models.forms.MedicalHospitalization = {
    models.forms.MedicalHospitalization(this.fiveYears, this.fiveYearsDetails) 
  }
}

object MedicalHospitalization {

  val parser: RowParser[MedicalHospitalization] = {
    get[String]("medical_hospitalization_5years") ~
    get[Option[String]]("medical_hospitalization_details") map {
      case fiveYears ~ fiveYearsDetails => {
        MedicalHospitalization(fiveYears, fiveYearsDetails)
      }
    }
  }

}