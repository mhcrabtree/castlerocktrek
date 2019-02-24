package contexts

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.Logger

import models.db.{ Context }

trait BaseContext extends InjectedController {

  def BaseAction()(f: Context => Int => Request[AnyContent] => Result) = InitParams { params => implicit request: Request[AnyContent] =>
    val timestamp = common.Utility.currentTimestampInSeconds

    val context: Context = Context.create(request)

    f(context)(timestamp)(request)
  }

  def InitParams(f: collection.mutable.Map[String, String] => Request[AnyContent] => Result) = Action { implicit request =>
    f(getParams)(request)
  }

  def getFormParams(implicit request: Request[AnyContent]): Map[String, String] = request.body.asMultipartFormData match {
    case None => Map.empty
    case Some(b) =>
      val t = b.asFormUrlEncoded match {
        case s: Map[String, Seq[String]] => s
      }
      for ((k, v) <- t) yield k -> v.headOption.getOrElse(throw new IllegalStateException())
  }

  def getUrlEncodedParams(implicit request: Request[AnyContent]): Map[String, String] = request.body.asFormUrlEncoded match {
    case None => Map.empty
    case Some(b) => for ((k, v) <- b) yield k -> v.headOption.getOrElse(throw new IllegalStateException())
  }

  def getQueryParams(implicit request: Request[AnyContent]): Map[String, String] = for ((k, v) <- request.queryString; if !v.headOption.contains("")) yield {
    k -> v.headOption.getOrElse(throw new IllegalStateException())
  }

  def getParams(implicit request: Request[AnyContent]): collection.mutable.Map[String, String] = {
    val params: collection.mutable.Map[String, String] = collection.mutable.Map()
    var paramsData: Map[String, String] = getFormParams
    val paramsEncoded: Map[String, String] = getUrlEncodedParams
    val queryData: Map[String, String] = getQueryParams

    params ++= paramsData ++= paramsEncoded ++= queryData
  }

}
