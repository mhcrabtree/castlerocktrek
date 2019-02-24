package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class RegistrationControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "RegistrationController GET" should {

/*
    "render the index page from a new instance of controller" in {
      val controller = new RegistrationController(stubControllerComponents())
      val index = controller.index("abc").apply(FakeRequest(GET, "/registration"))

      status(index) mustBe OK
      contentType(index) mustBe Some("text/html")
      contentAsString(index) must include ("Mobile App Registration")
    }

    "render the index page from the application" in {
      val controller = inject[RegistrationController]
      val index = controller.index("abc").apply(FakeRequest(GET, "/registration"))

      status(index) mustBe OK
      contentType(index) mustBe Some("text/html")
      contentAsString(index) must include ("Mobile App Registration")
    }
*/

    "render the index page from the router" in {
      val request = FakeRequest(GET, "/registration")
      val index = route(app, request).get

      status(index) mustBe OK
      contentType(index) mustBe Some("text/html")
      contentAsString(index) must include ("Mobile App Registration")
    }
  }
}
