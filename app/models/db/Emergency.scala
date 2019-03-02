package models.db

import play.api.db._

import anorm._
import anorm.SqlParser._

case class Emergency(
  name: Name,
  phone: String 
) {
  lazy val toForm: models.forms.Emergency = models.forms.Emergency(this.name.toForm, this.phone) 
}

object Emergency {

  val parser: RowParser[Emergency] = {
    Name.parserEmergency ~
    get[String]("emergency_contact_phone") map {
      case name ~ phone => Emergency(name, phone)
    }
  }

}