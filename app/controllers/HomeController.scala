package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.filters.csrf._
import play.filters.csrf.CSRF.Token

import play.api.db.Database

import contexts.BaseContext

import play.api.Configuration

import models.logging.Logging
import org.joda.time._

@Singleton
class HomeController @Inject()(db: Database, cc: ControllerComponents, config: Configuration) extends BaseContext with play.api.i18n.I18nSupport {

  def index() = BaseAction() { context => timestamp => implicit request: Request[AnyContent] =>
    val view = views.html.home.index()

    Ok(view).as(HTML)
  }

}