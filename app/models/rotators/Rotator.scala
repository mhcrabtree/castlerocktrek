package models.rotators

import java.util.concurrent.ConcurrentHashMap
import scala.collection.JavaConverters._
import scala.collection._
import common.MathUtility

case class Rotator(
  ratio: Double
) {
  // figure out the range which will determine if the rotation is a hit or a miss
  val denomiator: Int = 100 // the larger this number the more precise the rotation will be
  val numerator: Int = (MathUtility.roundAt(2)(this.ratio) * this.denomiator.toDouble).toInt
  val gcd: Int = MathUtility.gcd(numerator, denomiator)

  // below are the values that actually matter for the rotation
  val ceiling: Int = numerator / gcd
  val reset: Int = denomiator / gcd

  @volatile var counter: Int = 0

  /** Return a true if counter is less than or equal to the ceiling
   *
   */
  def rotate: Boolean = {
    this.counter = if (this.counter < this.reset) this.counter + 1 else 1
    (this.counter <= ceiling)
  }
}

object Rotator {
  private val rotators: concurrent.Map[String, Rotator] = new ConcurrentHashMap[String, Rotator].asScala

  def forKey(key: String, ratio: Double): Boolean = {

    val k: Option[Rotator] = {
      this.rotators.get(key) match {
        case Some(r) if r.ratio == ratio => Some(r)
        case _ => this.rotators.put(key, Rotator(ratio))
      }
    }

    k.map(_.rotate).getOrElse(false)
  }
}
