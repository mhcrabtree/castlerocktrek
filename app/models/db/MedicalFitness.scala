package models.db

import play.api.db._

import anorm._
import anorm.SqlParser._

case class MedicalFitness(
  level: String
) {
  lazy val toForm: models.forms.MedicalFitness = {
    models.forms.MedicalFitness(this.level) 
  }
}

object MedicalFitness {

  val parser: RowParser[MedicalFitness] = {
    get[String]("medical_fitness_level") map {
      case level => {
        MedicalFitness(level)
      }
    }
  }

  val options: Seq[(String, String)] = {
    Seq(
      ("regular", "Regularly participates in physical activities."),
      ("occasional", "Occasionally exercises or does physical activities."),
      ("often", "Often exercises or does physical activities and feels very fit."),
      ("constant", "Exercises or does physical activities such as sporting teams and is very fit."),
    )
  }
  
}