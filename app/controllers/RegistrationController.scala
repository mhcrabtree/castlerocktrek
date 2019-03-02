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
    val onRegistrationID: Option[Int] = request.session.get("registrationID").map(_.toInt)

    onRegistrationID.flatMap { id =>
      models.db.Registration.find(db, id).map(_.toPage1).map { formData =>
        Ok(views.html.registration.ward(models.forms.Page1.post(db).fill(formData), models.db.Ward.options(db), models.db.Organization.options(db)))
      }
    }.getOrElse {
      Ok(views.html.registration.ward(models.forms.Page1.post(db), models.db.Ward.options(db), models.db.Organization.options(db)))
    }
  }
  
  def postPage1() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    val onRegistrationID: Option[Int] = request.session.get("registrationID").map(_.toInt)

    models.forms.Page1.post(db).bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.registration.ward(formWithErrors, models.db.Ward.options(db), models.db.Organization.options(db)))
      },
      data => {
        val newID: Option[Int] = onRegistrationID.flatMap { id =>
          models.db.Registration.updatePage1(db, data, id)
        }.orElse {
          models.db.Registration.insertPage1(db, data, context)
        }

        newID.map { id =>
          Redirect(routes.RegistrationController.page2()).withSession("page" -> "2", "registrationID" -> id.toString)
        }.getOrElse {
          BadRequest(views.html.registration.error()).as(HTML)
        }
      }
    )
  }

  /** Page 2
   *
   */
  def page2() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    val onRegistrationID: Option[Int] = request.session.get("registrationID").map(_.toInt)

    onRegistrationID.flatMap { id =>
      models.db.Registration.find(db, id).map(_.toPage2).map { formData =>
        Ok(views.html.registration.child(models.forms.Page2.post(db).fill(formData), models.db.Gender.options(db)))
      }
    }.getOrElse {
      BadRequest(views.html.registration.error()).as(HTML)
    }
  }
  
  def postPage2() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    val onRegistrationID: Option[Int] = request.session.get("registrationID").map(_.toInt)

    models.forms.Page2.post(db).bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.registration.child(formWithErrors, models.db.Gender.options(db)))
      },
      data => {
        val newID: Option[Int] = models.db.Registration.updatePage2(db, data, onRegistrationID.get)

        newID.map { id =>
          Redirect(routes.RegistrationController.page3()).withSession("page" -> "3", "registrationID" -> id.toString)
        }.getOrElse {
          BadRequest(views.html.registration.error()).as(HTML)
        }
      }
    )
  }

  /** Page 3
   *
   */
  def page3() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    val onRegistrationID: Option[Int] = request.session.get("registrationID").map(_.toInt)

    onRegistrationID.flatMap { id =>
      models.db.Registration.find(db, id).map(_.toPage3).map { formData =>
        Ok(views.html.registration.parents(models.forms.Page3.post(db).fill(formData)))
      }
    }.getOrElse {
      BadRequest(views.html.registration.error()).as(HTML)
    }
  }
  
  def postPage3() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    val onRegistrationID: Option[Int] = request.session.get("registrationID").map(_.toInt)

    models.forms.Page3.post(db).bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.registration.parents(formWithErrors))
      },
      data => {
        val newID: Option[Int] = models.db.Registration.updatePage3(db, data, onRegistrationID.get)

        newID.map { id =>
          Redirect(routes.RegistrationController.page4()).withSession("page" -> "4", "registrationID" -> id.toString)
        }.getOrElse {
          BadRequest(views.html.registration.error()).as(HTML)
        }
      }
    )
  }

  /** Page 4
   *
   */
  def page4() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    val onRegistrationID: Option[Int] = request.session.get("registrationID").map(_.toInt)

    onRegistrationID.flatMap { id =>
      models.db.Registration.find(db, id).map(_.toPage4).map { formData =>
        Ok(views.html.registration.emergency(models.forms.Page4.post(db).fill(formData)))
      }
    }.getOrElse {
      BadRequest(views.html.registration.error()).as(HTML)
    }
  }
  
  def postPage4() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    val onRegistrationID: Option[Int] = request.session.get("registrationID").map(_.toInt)

    models.forms.Page4.post(db).bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.registration.emergency(formWithErrors))
      },
      data => {
        val newID: Option[Int] = models.db.Registration.updatePage4(db, data, onRegistrationID.get)

        newID.map { id =>
          Redirect(routes.RegistrationController.page5()).withSession("page" -> "5", "registrationID" -> id.toString)
        }.getOrElse {
          BadRequest(views.html.registration.error()).as(HTML)
        }
      }
    )
  }

  /** Page 5
   *
   */
  def page5() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    val onRegistrationID: Option[Int] = request.session.get("registrationID").map(_.toInt)

    onRegistrationID.flatMap { id =>
      models.db.Registration.find(db, id).map { data =>
        Ok(views.html.registration.consent(data)).as(HTML)
      }
    }.getOrElse {
      BadRequest(views.html.registration.error()).as(HTML)
    }
  }
  
  def postPage5() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    request.session.get("registrationID").map(_.toInt).map { id =>
      Redirect(routes.RegistrationController.page6()).withSession("page" -> "6", "registrationID" -> id.toString)
    }.getOrElse {
      BadRequest(views.html.registration.error()) 
    }
  }

  /** Page 6
   *
   */
  def page6() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    val onRegistrationID: Option[Int] = request.session.get("registrationID").map(_.toInt)

    onRegistrationID.flatMap { id =>
      models.db.Registration.find(db, id).map(_.toPage6).map { formData =>
        Ok(views.html.registration.medical(models.forms.Page6.post.fill(formData), YesNo.options, MedicalFitness.options))
      }
    }.getOrElse {
      BadRequest(views.html.registration.error()).as(HTML)
    }
  }
  
  def postPage6() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    val onRegistrationID: Option[Int] = request.session.get("registrationID").map(_.toInt)

    models.forms.Page6.post.bindFromRequest.fold(
      formWithErrors => {
        val view = views.html.registration.medical(formWithErrors, YesNo.options, MedicalFitness.options)
        BadRequest(view).as(HTML)
      },
      data => {
        val newID: Option[Int] = models.db.Registration.updatePage6(db, data, onRegistrationID.get)

        newID.map { id =>
          Redirect(routes.RegistrationController.completed()).withSession("page" -> "thankyou", "registrationID" -> id.toString)
        }.getOrElse {
          BadRequest("Oops, something has gone wrong!")
        }
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