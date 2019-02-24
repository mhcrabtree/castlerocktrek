package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.filters.csrf._
import play.filters.csrf.CSRF.Token

import play.api.db.Database

import contexts.BaseContext

import play.api.Configuration

import models.forms.{ Registration }
import models.db._

import models.logging.Logging
import org.joda.time._


@Singleton
class RegistrationController @Inject()(db: Database, cc: ControllerComponents, config: Configuration) extends BaseContext with play.api.i18n.I18nSupport {

  val pages: Seq[String] = Seq("ward", "child", "parents", "emergency", "consent", "medical")


  def index() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    Redirect(routes.RegistrationController.page1())
  }

  /** Page 1
   *
   */
  def page1() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    val view = views.html.registration.ward(Registration.post(db), Ward.options(db))

    Ok(view).as(HTML)
  }

  
  def postPage1() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>

    Registration.post(db).bindFromRequest.fold(
      formWithErrors => {
        BadRequest("There was an error processing the form...").as(TEXT)
      },
      data => {
        println(data)
        // save to db
        Ok("Goodbye World")
      }
    )

  }


  /** Page 2
   *
   */
  def page2() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    val view = views.html.registration.child(Registration.post(db))

    Ok(view).as(HTML)
  }

  
  def postPage2() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>

    Registration.post(db).bindFromRequest.fold(
      formWithErrors => {
        BadRequest("There was an error processing the form...").as(TEXT)
      },
      data => {
        println(data)
        // save to db
        Ok("Goodbye World")
      }
    )

  }


  /** Page 3
   *
   */
  def page3() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    val view = views.html.registration.parents(Registration.post(db))

    Ok(view).as(HTML)
  }

  
  def postPage3() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>

    Registration.post(db).bindFromRequest.fold(
      formWithErrors => {
        BadRequest("There was an error processing the form...").as(TEXT)
      },
      data => {
        println(data)
        // save to db
        Ok("Goodbye World")
      }
    )

  }


  /** Page 4
   *
   */
  def page4() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    val view = views.html.registration.emergency(Registration.post(db))

    Ok(view).as(HTML)
  }

  
  def postPage4() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>

    Registration.post(db).bindFromRequest.fold(
      formWithErrors => {
        BadRequest("There was an error processing the form...").as(TEXT)
      },
      data => {
        println(data)
        // save to db
        Ok("Goodbye World")
      }
    )

  }


  /** Page 5
   *
   */
  def page5() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    val view = views.html.registration.medical(Registration.post(db))

    Ok(view).as(HTML)
  }

  
  def postPage5() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>

    Registration.post(db).bindFromRequest.fold(
      formWithErrors => {
        BadRequest("There was an error processing the form...").as(TEXT)
      },
      data => {
        println(data)
        // save to db
        Ok("Goodbye World")
      }
    )

  }


  /** Page 6
   *
   */
  def page6() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    val view = views.html.registration.consent(Registration.post(db))

    Ok(view).as(HTML)
  }

  
  def postPage6() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>

    Registration.post(db).bindFromRequest.fold(
      formWithErrors => {
        BadRequest("There was an error processing the form...").as(TEXT)
      },
      data => {
        println(data)
        // save to db
        Ok("Goodbye World")
      }
    )

  }

  /** Thank You
   *
   */
  def completed() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    val view = views.html.registration.thanks()

    Ok(view).as(HTML)
  }


}