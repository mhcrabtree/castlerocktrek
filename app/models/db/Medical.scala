package models.db

import play.api.db._

import anorm._
import anorm.SqlParser._

case class Medical(
  condition: MedicalCondition,
  medication: MedicalMedication,
  alergy: MedicalAlergy,
  hospitalization: MedicalHospitalization,
  mental: MedicalMental,
  fitness: MedicalFitness,
  other: MedicalOther,
  consent: MedicalConsent
) {
  lazy val toForm: models.forms.Page6 = {
    models.forms.Page6(this.condition.toForm, this.medication.toForm, this.alergy.toForm, this.hospitalization.toForm, this.mental.toForm, this.fitness.toForm, this.other.toForm, this.consent.toForm) 
  }
}

object Medical {

  val parser: RowParser[Medical] = {
    MedicalCondition.parser ~
    MedicalMedication.parser ~ 
    MedicalAlergy.parser ~ 
    MedicalHospitalization.parser ~ 
    MedicalMental.parser ~ 
    MedicalFitness.parser ~ 
    MedicalOther.parser ~ 
    MedicalConsent.parser map {
      case condition ~ medication ~ alergy ~ hospitalization ~ mental ~ fitness ~ other ~ consent => {
        Medical(condition, medication, alergy, hospitalization, mental, fitness, other, consent)
      }
    }
  }

}