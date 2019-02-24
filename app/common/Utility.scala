package common

import scala.concurrent._
import org.joda.time._
import org.joda.time.format._

import org.apache.commons.codec.binary.Base64
import java.io.{IOException, ByteArrayOutputStream, ByteArrayInputStream}
import java.net.{URL,MalformedURLException}
import java.util.zip.{GZIPInputStream, GZIPOutputStream}
import sys.process._
import java.io._
import scala.language.postfixOps

object Utility {
  val myIp: String = java.net.InetAddress.getLocalHost.getHostAddress
  val myHostName: String = java.net.InetAddress.getLocalHost.getHostName
  val myHostNameStats: String = {
    this.myHostName.split("\\.").map(_.capitalize).mkString("")
  }

  def now = {
    new DateTime(DateTimeZone.UTC)
  }

  def day: String = {
    this.dateTimeToStringShort(this.now)
  }

  def dateTimeIso: String = {
    this.dateTimeToStringIso(this.now)
  }

  def timestampToDate(timestamp: Long): java.util.Date = {
//    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    val time: java.util.Date = new java.util.Date(timestamp*1000L);
    time
  }

  def dateTimeToString(date: DateTime): String = {
    val dateTime = date.toDateTime()
    val formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
    formatter.print(dateTime)
  }

  def dateTimeToStringShort(date: DateTime): String = {
    val dateTime = date.toDateTime() //(DateTimeZone.UTC)
    val formatter = DateTimeFormat.forPattern("yyyy-MM-dd")
    formatter.print(dateTime)
  }

  def dateTimeToStringIso(date: DateTime): String = {
    val formatter = ISODateTimeFormat.dateTime()
    formatter.print(date)
  }

  def currentTimestampInSeconds: Int = {
    (System.currentTimeMillis() / 1000L).toInt
  }

  def currentTimestampInMinutes: Int = {
    ((System.currentTimeMillis() / 1000L) / 60L).toInt
  }

  def currentTimestampInHours: Int = {
    ((System.currentTimeMillis() / 1000L) / 3600L).toInt
  }

  def compressString(text: String): String = {
    val outputStream: ByteArrayOutputStream = new ByteArrayOutputStream()
    val gzipOutput: GZIPOutputStream = new GZIPOutputStream(outputStream)

    try {
      gzipOutput.write(text.getBytes)
    } catch {
      case e: IOException => println("Error") //Logger.error("Error writing to Gzip output stream", e)
      if(gzipOutput != null) gzipOutput.close
      if(outputStream != null) outputStream.close
      throw e
    } finally {
      if(gzipOutput != null) gzipOutput.close
      if(outputStream != null) outputStream.close
    }

    // base64 encode
    Base64.encodeBase64String(outputStream.toByteArray)
  }

  // see https://github.com/kafka-dev/kafka/blob/master/core/src/main/scala/kafka/message/CompressionUtils.scala
  def decompressString(base64EncodedText: String): Option[String] = {
    try {
      val outputStream: ByteArrayOutputStream = new ByteArrayOutputStream()
      val inputStream: ByteArrayInputStream = new ByteArrayInputStream(Base64.decodeBase64(base64EncodedText))
      val gzipInput: GZIPInputStream = new GZIPInputStream(inputStream)

      val buffer = new Array[Byte](1024)

      try {
        Stream.continually(gzipInput.read(buffer)).takeWhile(_ > 0).foreach { dataRead =>
          outputStream.write(buffer, 0, dataRead)
        }
        Some(new String(outputStream.toByteArray))
      } catch {
        case e:IOException => println("Error while reading from gzip input", e)
        if (gzipInput != null) gzipInput.close
        if (outputStream != null) outputStream.close
        if (inputStream != null) inputStream.close
        None
      } finally {
        if (gzipInput != null) gzipInput.close
        if (outputStream != null) outputStream.close
        if (inputStream != null) inputStream.close
      }

    } catch {
      case e: Throwable => println("Argh!")
      None
    } finally {

    }
  }

  /**
   * Calculate the MD5 hash for the specified input
   *
   * @param The string to be encrypted
   */
  def md5(s:String): String = {
    import java.security.MessageDigest
    MessageDigest.getInstance("MD5").digest(s.getBytes).map("%02X".format(_)).mkString.toLowerCase
  }

  def randomUuid(): String = java.util.UUID.randomUUID.toString

  val rnd = new scala.util.Random
  def randomIntForSize(size: Int): Int = this.rnd.nextInt(size)

  def parseDomainFromUrl(url: String): String = {
    {
      try {
        val temp = new URL(url)
        temp.getHost
      } catch {
        case e: MalformedURLException => url // for now just return what the string was so we can see it
      }
    }.toLowerCase
  }

  def hashMapMacroReplace(hashMap: Map[String, String], data: String, stripStrayMacros: Boolean = true, urlEncode: Boolean = true): String = {
    var temp: String = data
    hashMap.map { case (k, v) =>
      temp = {
        if (urlEncode) temp.replaceAll("\\{(?i)%s\\}".format(k), java.net.URLEncoder.encode(v, "UTF-8"))
        else temp.replaceAll("\\{(?i)%s\\}".format(k), v.replace("$", "\\$"))
      }
    }

    if (stripStrayMacros) temp.replaceAll("\\{.*?\\}", "") else temp
  }

}