package models.db

import play.api.mvc.{ Request, AnyContent }
import play.api.libs.json._

case class Context(
  ips: List[String] = Nil,
  userAgent: Option[String] = None,
  referer: Option[String] = None,
  country: Option[String] = None,
  dnt: Boolean
) {
  val ip: Option[String] = ips.headOption

  val json: JsValue = Json.obj(
    "ips" -> this.ips,
    "ip" -> this.ip,
    "user_agent" -> this.userAgent,
    "referer" -> this.referer,
    "country" -> this.country,
    "dnt" -> this.dnt
  )

}

object Context {
	
  def create(request: Request[AnyContent]): Context = {
    val headers: Map[String, Seq[String]] = request.headers.toMap

    val ip: String = request.remoteAddress
    val ips: List[String] = List(ip) ++ headers.get("X-Forwarded-For").map(_(0)).map { h =>
      h.split(",").map(_.replaceAll("\\s", "")).toList
    }.getOrElse(Nil).distinct

    val referer: Option[String] = headers.get("Referer").map(_(0))
    val ua: Option[String] = headers.get("User-Agent").map(_(0))
    val country: Option[String] = headers.get("CF-IPCountry").map(_(0))
    val dnt: Boolean = headers.get("DNT").map(_(0)) match {
      case Some("1") => true
      case _ => false
    }

    Context(ips, ua, referer, country, dnt)
  }
  
}
