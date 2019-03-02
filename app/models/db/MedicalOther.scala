package models.db

import play.api.db._

import anorm._
import anorm.SqlParser._

case class MedicalOther(
  concernsDetails: Option[String] = None
) {
  lazy val toForm: models.forms.MedicalOther = {
    models.forms.MedicalOther(this.concernsDetails) 
  }
}

object MedicalOther {

  val parser: RowParser[MedicalOther] = {
    get[Option[String]]("medical_other_concerns_details") map {
      case concernsDetails => {
        MedicalOther(concernsDetails)
      }
    }
  }

}