package models.db

import play.api._
import play.api.db._

import anorm._
import anorm.SqlParser._

case class Registration(
  id: Int,
  ward: Ward,
  organization: Organization,
  child: Child
) {

  def toPage1: models.forms.Page1 = models.forms.Page1(
    organization = models.forms.Id(this.organization.id),
    ward = models.forms.Id(this.ward.id) 
  )

  def toPage2: models.forms.Page2 = models.forms.Page2(
    name = this.child.name.toForm,
    dob = this.child.dob,
    gender = this.child.gender
  )

}

object Registration {
  
  def parser(db: Database): RowParser[Registration] = {
    get[Int]("id") ~
    get[Int]("ward_id") ~
    get[Int]("organization_id") ~
    Child.parser map {
      case id ~ wardID ~ organizationID ~ child => {
        val ward = Ward.find(db, wardID).get
        val organization = Organization.find(db, organizationID).get
        Registration(id, ward, organization, child)
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
 
  def insertPage1(db: Database, data: models.forms.Page1): Option[Int] = db.withConnection { implicit connection =>
    SQL("""
      |INSERT INTO registration (ward_id, organization_id) VALUES ({wardID}, {organizationID})
    """.stripMargin).on(
      "wardID" -> data.ward.id,
      "organizationID" -> data.organization.id
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
      |  child_gender = {gender}
      |WHERE id = {ID}
    """.stripMargin).on(
      "nameFirst" -> data.name.first,
      "nameMiddle" -> data.name.middle,
      "nameLast" -> data.name.last,
      "dob" -> data.dob,
      "gender" -> data.gender,
      "ID" -> id
    ).execute()

    Some(id)
  } 

}