package controllers

import javax.inject._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def rbi() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.rbiLogin())
  }

  def banks() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.bankLogin())
  }

  def representative() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.repLogin())
  }
}
