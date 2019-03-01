package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.Database

object Gender {
  
  def id(db: Database): Form[Id] = Form(
    mapping(
      "id" -> number.verifying("error.invalid", v => (models.db.Gender.findActive(db, v).isDefined))
    )(Id.apply)(Id.unapply)
  )
  
}