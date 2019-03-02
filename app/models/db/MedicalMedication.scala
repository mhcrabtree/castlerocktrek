package models.db

import play.api.db._

import anorm._
import anorm.SqlParser._

case class MedicalMedication(
  regularDetails: Option[String] = None,
  asNeededDetails: Option[String] = None
) {
  lazy val toForm: models.forms.MedicalMedication = {
    models.forms.MedicalMedication(this.regularDetails, this.asNeededDetails) 
  }
}

object MedicalMedication {

  val parser: RowParser[MedicalMedication] = {
    get[Option[String]]("medical_medications_regular_details") ~
    get[Option[String]]("medical_medications_asneeded_details") map {
      case regular ~ asNeeded => {
        MedicalMedication(regular, asNeeded)
      }
    }
  }

}