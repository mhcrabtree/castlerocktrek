package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.Database

object Ward {
	
 	def id(db: Database): Form[Id] = Form(
 		mapping(
 			"id" -> number
// 			"id" -> number.verifying("error.invalid", id => (Ward.find(db, id).isDefined))
 		)(Id.apply)(Id.unapply)
	)
	
}