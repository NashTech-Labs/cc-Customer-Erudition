package controllers

import javax.inject._
import play.api._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def login() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.login())
  }

  def superUserDashBoard()=Action{implicit request:Request[AnyContent]=>
    //fetch name of super user and send as input to the below
    Ok(views.html.superUserDashboard("Super User"))
  }

  def bankDashBoard()=Action{implicit request:Request[AnyContent]=>
    //fetch name of super user and send as input to the below
    Ok(views.html.bankDashboard("SBI"))
  }

  def repDashBoard()=Action{implicit request:Request[AnyContent]=>
    //fetch name of super user and send as input to the below
    Ok(views.html.agentDashboard("Rana"))
  }
}
