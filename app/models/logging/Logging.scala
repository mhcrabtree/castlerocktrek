package models.logging

import play.api.Configuration
import play.api.Logger
import models.rotators.Rotator

import java.util.concurrent.ConcurrentHashMap
import scala.collection.JavaConverters._
import scala.collection._

case class Log(
  key: String,        // the name of the filter in logback
  sampleRate: Double  // a number < 1 which results in a hole number when divided by 1 (i.e. 1 / .01 = 100)
) {
  val logger = Logger(key)
}

/** Configuration for all logging within this application.  The Rotator is used so make sure settings are correct in .conf
 *
 */
object Logging {
  private val logs: concurrent.Map[String, Log] = new ConcurrentHashMap[String, Log].asScala

  def init(config: Configuration) {
    //this.logs.put("name", Log("name", config.getOptional[Double]("app.settings.logging.name.sampleRate").getOrElse(1.0)))
  }

  def info(key: String, record: String, force: Boolean = false) {
    this.logs.get(key).map { l =>
      if (force || Rotator.forKey(key, l.sampleRate)) l.logger.info(record)
    }
  }

  def warn(key: String, record: String, force: Boolean = false) {
    this.logs.get(key).map { l =>
      if (force || Rotator.forKey(key, l.sampleRate)) l.logger.warn(record)
    }
  }

  def error(key: String, record: String, force: Boolean = false) {
    this.logs.get(key).map { l =>
      if (force || Rotator.forKey(key, l.sampleRate)) l.logger.error(record)
    }
  }

  def debug(key: String, record: String, force: Boolean = false) {
    this.logs.get(key).map { l =>
      if (force || Rotator.forKey(key, l.sampleRate)) l.logger.debug(record)
    }
  }
}