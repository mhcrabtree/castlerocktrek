package models.db

import play.api._
import play.api.db._

import anorm._
import anorm.SqlParser._

case class Registration(
  id: Int,
  ward: Ward,
  organization: Organization,
  child: Child,
  mother: Parent,
  father: Parent,
  emergency: Emergency,
  medical: Medical
) {

  val consentNames: String = {
    if (this.organization.id == 5) {
      "%s".format(this.child.name.full)
    } else if (this.mother.name.full == this.father.name.full) {
      "%s".format(this.mother.name.full) // they're the same
    } else {
      "%s and %s".format(this.mother.name.full, this.father.name.full)
    }
  }

  def toPage1: models.forms.Page1 = models.forms.Page1(
    organization = models.forms.Id(this.organization.id),
    ward = models.forms.Id(this.ward.id) 
  )

  def toPage2: models.forms.Page2 = models.forms.Page2(
    name = this.child.name.toForm,
    dob = this.child.dob,
    gender = models.forms.Id(this.child.gender.id)
  )

  def toPage3: models.forms.Page3 = models.forms.Page3(
    mother = this.mother.toForm,
    father = this.father.toForm
  )  

  def toPage3AdultMale: models.forms.Page3AdultMale = models.forms.Page3AdultMale(
    father = this.father.toForm
  )

  def toPage3AdultFemale: models.forms.Page3AdultFemale = models.forms.Page3AdultFemale(
    mother = this.mother.toForm
  )

  def toPage4: models.forms.Page4 = models.forms.Page4(
    emergency = this.emergency.toForm
  )  

  def toPage6: models.forms.Page6 = this.medical.toForm

}

object Registration {
  
  def parser(db: Database): RowParser[Registration] = {
    get[Int]("id") ~
    get[Int]("ward_id") ~
    get[Int]("organization_id") ~
    Child.parser(db) ~
    Parent.parserMother ~
    Parent.parserFather ~
    Emergency.parser ~
    Medical.parser map {
      case id ~ wardID ~ organizationID ~ child ~ mother ~ father ~ emergency ~ medical => {
        val ward = Ward.find(db, wardID).get
        val organization = Organization.find(db, organizationID).get
        Registration(id, ward, organization, child, mother, father, emergency, medical)
      }
    }
  }

  def list(db: Database): List[Registration] = db.withConnection { implicit connection =>
    SQL("""
      |SELECT *
      |FROM registration 
      |ORDER BY id 
    """.stripMargin).as(parser(db).*)
  }
  
  def find(db: Database, id: Int): Option[Registration] = db.withConnection { implicit connection =>
    SQL("""
      |SELECT *
      |FROM registration 
      |WHERE id = {ID}
    """.stripMargin).on("ID" -> id).as(parser(db).singleOpt)
  }

  def insertPage1(db: Database, data: models.forms.Page1, context: models.db.Context): Option[Int] = db.withConnection { implicit connection =>
    SQL("""
      |INSERT INTO registration (ward_id, organization_id, ip, ips, user_agent, context)
      |VALUES ({wardID}, {organizationID}, {ip}, {ips}, {userAgent}, {context})
    """.stripMargin).on(
      "wardID" -> data.ward.id,
      "organizationID" -> data.organization.id,
      "ip" -> context.ip.getOrElse("unknown"),
      "ips" -> context.ips.mkString(","),
      "userAgent" -> context.userAgent.getOrElse("unknown"),
      "context" -> context.json.toString
    ).executeInsert().map(_.toInt)
  } 

  def updatePage1(db: Database, data: models.forms.Page1, id: Int): Option[Int] = db.withConnection { implicit connection =>
    SQL("""
      |UPDATE registration SET
      |  ward_id = {wardID},
      |  organization_id = {organizationID}
      |WHERE id = {ID}
    """.stripMargin).on(
      "wardID" -> data.ward.id,
      "organizationID" -> data.organization.id,
      "ID" -> id
    ).execute()

    Some(id)
  } 

  def updatePage2(db: Database, data: models.forms.Page2, id: Int): Option[Int] = db.withConnection { implicit connection =>
    SQL("""
      |UPDATE registration SET
      |  child_name_first = {nameFirst},
      |  child_name_middle = {nameMiddle},
      |  child_name_last = {nameLast},
      |  child_dob = {dob},
      |  child_gender_id = {genderID}
      |WHERE id = {ID}
    """.stripMargin).on(
      "nameFirst" -> data.name.first,
      "nameMiddle" -> data.name.middle,
      "nameLast" -> data.name.last,
      "dob" -> data.dob,
      "genderID" -> data.gender.id,
      "ID" -> id
    ).execute()

    Some(id)
  }

  def updatePage3(db: Database, data: models.forms.Page3, id: Int): Option[Int] = db.withConnection { implicit connection =>
    SQL("""
      |UPDATE registration SET
      |  parent_mother_name_first = {motherNameFirst},
      |  parent_mother_name_last = {motherNameLast},
      |  parent_mother_contact_phone = {motherContactPhone},
      |  parent_mother_contact_email = {motherContactEmail},
      |  parent_father_name_first = {fatherNameFirst},
      |  parent_father_name_last = {fatherNameLast},
      |  parent_father_contact_phone = {fatherContactPhone},
      |  parent_father_contact_email = {fatherContactEmail}      
      |WHERE id = {ID}
    """.stripMargin).on(
      "motherNameFirst" -> data.mother.name.first,
      "motherNameLast" -> data.mother.name.last,
      "motherContactPhone" -> data.mother.contact.phone,
      "motherContactEmail" -> data.mother.contact.email,
      "fatherNameFirst" -> data.father.name.first,
      "fatherNameLast" -> data.father.name.last,
      "fatherContactPhone" -> data.father.contact.phone,
      "fatherContactEmail" -> data.father.contact.email,
      "ID" -> id
    ).execute()

    Some(id)
  } 

  def updatePage3AM(db: Database, data: models.forms.Page3AdultMale, id: Int): Option[Int] = db.withConnection { implicit connection =>
    SQL("""
      |UPDATE registration SET
      |  parent_father_contact_phone = {fatherContactPhone},
      |  parent_father_contact_email = {fatherContactEmail}      
      |WHERE id = {ID}
    """.stripMargin).on(
      "fatherContactPhone" -> data.father.contact.phone,
      "fatherContactEmail" -> data.father.contact.email,
      "ID" -> id
    ).execute()

    Some(id)
  } 

  def updatePage3AF(db: Database, data: models.forms.Page3AdultFemale, id: Int): Option[Int] = db.withConnection { implicit connection =>
    SQL("""
      |UPDATE registration SET
      |  parent_mother_contact_phone = {motherContactPhone},
      |  parent_mother_contact_email = {motherContactEmail}
      |WHERE id = {ID}
    """.stripMargin).on(
      "motherContactPhone" -> data.mother.contact.phone,
      "motherContactEmail" -> data.mother.contact.email,
      "ID" -> id
    ).execute()

    Some(id)
  } 

  def updatePage4(db: Database, data: models.forms.Page4, id: Int): Option[Int] = db.withConnection { implicit connection =>
    SQL("""
      |UPDATE registration SET
      |  emergency_name_first = {emergencyNameFirst},
      |  emergency_name_last = {emergencyNameLast},
      |  emergency_contact_phone = {emergencyContactPhone}
      |WHERE id = {ID}
    """.stripMargin).on(
      "emergencyNameFirst" -> data.emergency.name.first,
      "emergencyNameLast" -> data.emergency.name.last,
      "emergencyContactPhone" -> data.emergency.phone,
      "ID" -> id
    ).execute()

    Some(id)
  }

  def updatePage6(db: Database, data: models.forms.Page6, id: Int): Option[Int] = db.withConnection { implicit connection =>
    SQL("""
      |UPDATE registration SET
      |  medical_conditions_provider_details = {providerDetails},
      |  medical_conditions_epilepsy = {epilepsy},
      |  medical_conditions_highbloodpressure = {highBloodPressure},
      |  medical_conditions_heartdisease = {heartDisease},
      |  medical_conditions_lungdisease = {lungDisease},
      |  medical_conditions_orthopedic = {orthopedic},
      |  medical_medications_regular_details = {regularDetails},
      |  medical_medications_asneeded_details = {asNeededDetails},
      |  medical_alergies_medications = {medications},
      |  medical_alergies_pollen = {pollen},
      |  medical_alergies_toxins = {toxins},
      |  medical_alergies_foods = {foods},
      |  medical_alergies_orthopedic = {alergiesOrthopedic},
      |  medical_hospitalization_5years = {fiveYears},
      |  medical_hospitalization_details = {fiveYearsDetails},
      |  medical_mental_concerns = {mentalConcerns},
      |  medical_mental_concerns_details = {mentalConcernsDetails},
      |  medical_fitness_level = {level},
      |  medical_other_concerns_details = {otherConcernsDetails},
      |  medical_consent_legal_given = {legalGiven}
      |WHERE id = {ID}
    """.stripMargin).on(
      "providerDetails" -> data.condition.providerDetails,
      "epilepsy" -> data.condition.epilepsy,
      "highBloodPressure" -> data.condition.highBloodPressure,
      "heartDisease" -> data.condition.heartDisease,
      "lungDisease" -> data.condition.lungDisease,
      "orthopedic" -> data.condition.orthopedic,
      "regularDetails" -> data.medication.regularDetails,
      "asNeededDetails" -> data.medication.asNeededDetails,
      "medications" -> data.alergy.medications,
      "pollen" -> data.alergy.pollen,
      "toxins" -> data.alergy.toxins,
      "foods" -> data.alergy.foods,
      "alergiesOrthopedic" -> data.alergy.orthopedic,
      "fiveYears" -> data.hospitalization.fiveYears,
      "fiveYearsDetails" -> data.hospitalization.fiveYearsDetails,
      "mentalConcerns" -> data.mental.concerns,
      "mentalConcernsDetails" -> data.mental.concernsDetails,
      "level" -> data.fitness.level,
      "otherConcernsDetails" -> data.other.concernsDetails,
      "legalGiven" -> data.consent.legalGiven,
      "ID" -> id
    ).execute()

    Some(id)
  }

}