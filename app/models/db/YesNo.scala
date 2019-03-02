package models.db

import play.api._
import play.api.db._

import anorm._
import anorm.SqlParser._

object YesNo {
  
  val options: Seq[(String, String)] = {
    Seq(
      ("no", "No"),
      ("yes", "Yes")
    )
  }

}
