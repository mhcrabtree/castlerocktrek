package models.forms

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._

import play.api.libs.json._

case class Name(
  first: String,
  middle: Option[String] = None,
  last: String
) {

}

object Name {
  val post: Form[Name] = Form(
    mapping(
      "first" -> nonEmptyText,
      "middle" -> optional(nonEmptyText),
      "last" -> nonEmptyText
    )(Name.apply)(Name.unapply)
  )
}