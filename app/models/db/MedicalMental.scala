package models.db

import play.api.db._

import anorm._
import anorm.SqlParser._

case class MedicalMental(
  concerns: String,
  concernsDetails: Option[String] = None
) {
  lazy val toForm: models.forms.MedicalMental = {
    models.forms.MedicalMental(this.concerns, this.concernsDetails) 
  }
}

object MedicalMental {

  val parser: RowParser[MedicalMental] = {
    get[String]("medical_mental_concerns") ~
    get[Option[String]]("medical_mental_concerns_details") map {
      case concerns ~ details => {
        MedicalMental(concerns, details)
      }
    }
  }

}