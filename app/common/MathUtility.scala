package common

object MathUtility {

  // greatest common denomiator
  // https://www.scala-lang.org/old/sites/default/files/linuxsoft_archives/docu/files/ScalaByExample.pdf
  def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

  // round to two decimal places
  // https://stackoverflow.com/questions/11106886/scala-doubles-and-precision
  def roundAt(p: Int)(n: Double): Double = {
    val s = math pow(10, p)
    (math round n * s) / s
  }

}