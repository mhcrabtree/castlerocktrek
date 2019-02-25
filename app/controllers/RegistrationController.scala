package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.filters.csrf._
import play.filters.csrf.CSRF.Token

import play.api.db.Database

import contexts.BaseContext

import play.api.Configuration

import models.db._
import models.forms.Registration // TODO remove this reference

import models.logging.Logging
import org.joda.time._


@Singleton
class RegistrationController @Inject()(db: Database, cc: ControllerComponents, config: Configuration) extends BaseContext with play.api.i18n.I18nSupport {

  val pages: Seq[String] = Seq("ward", "child", "parents", "emergency", "consent", "medical")

  def reset() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    Redirect(routes.RegistrationController.index()).withNewSession
  }

  def index() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    Redirect(routes.RegistrationController.page1()).withSession("page" -> "1")
  }

  /** Page 1
   *
   */
  def page1() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    val onPage: Option[Int] = request.session.get("page").map(_.toInt)
    val onRegistrationID: Option[Int] = request.session.get("registrationID").map(_.toInt)

    val view = onRegistrationID.flatMap { id =>
      models.db.Registration.find(db, id).map(_.toPage1).map { formData =>
        views.html.registration.ward(models.forms.Page1.post(db).fill(formData), models.db.Ward.options(db), models.db.Organization.options(db))
      }
    }.getOrElse {
      views.html.registration.ward(models.forms.Page1.post(db), models.db.Ward.options(db), models.db.Organization.options(db))
    }

    Ok(view).as(HTML)
  }
  
  def postPage1() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    val onPage: Option[Int] = request.session.get("page").map(_.toInt)
    val onRegistrationID: Option[Int] = request.session.get("registrationID").map(_.toInt)

    models.forms.Page1.post(db).bindFromRequest.fold(
      formWithErrors => {
        val view = views.html.registration.ward(formWithErrors, models.db.Ward.options(db), models.db.Organization.options(db))
        BadRequest(view).as(HTML)
      },
      data => {
        val newID: Option[Int] = onRegistrationID.flatMap { id =>
          models.db.Registration.updatePage1(db, data, id)
        }.orElse {
          models.db.Registration.insertPage1(db, data)
        }

        newID.map { id =>
          Redirect(routes.RegistrationController.page2()).withSession("page" -> "2", "registrationID" -> id.toString)
        }.getOrElse {
          BadRequest("Oops, something has gone wrong!")
        }
      }
    )
  }


  /** Page 2
   *
   */
  def page2() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    val onPage: Option[Int] = request.session.get("page").map(_.toInt)
    val onRegistrationID: Option[Int] = request.session.get("registrationID").map(_.toInt)

    val view = onRegistrationID.flatMap { id =>
      models.db.Registration.find(db, id).map(_.toPage2).map { formData =>
        views.html.registration.child(models.forms.Page2.post(db).fill(formData))
      }
    }.getOrElse {
      views.html.registration.child(models.forms.Page2.post(db))
    }

    Ok(view).as(HTML)
  }
  
  def postPage2() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    val onPage: Option[Int] = request.session.get("page").map(_.toInt)
    val onRegistrationID: Option[Int] = request.session.get("registrationID").map(_.toInt)

    models.forms.Page2.post(db).bindFromRequest.fold(
      formWithErrors => {
        val view = views.html.registration.child(formWithErrors)
        BadRequest(view).as(HTML)
      },
      data => {
        val newID: Option[Int] = models.db.Registration.updatePage2(db, data, onRegistrationID.get)

        newID.map { id =>
          Redirect(routes.RegistrationController.page3()).withSession("page" -> "3", "registrationID" -> id.toString)
        }.getOrElse {
          BadRequest("Oops, something has gone wrong!")
        }
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