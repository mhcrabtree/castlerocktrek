package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.Database

object Organization {
  
  def id(db: Database): Form[Id] = Form(
    mapping(
      "id" -> number.verifying("error.invalid", v => (models.db.Organization.find(db, v).isDefined))
    )(Id.apply)(Id.unapply)
  )
  
}