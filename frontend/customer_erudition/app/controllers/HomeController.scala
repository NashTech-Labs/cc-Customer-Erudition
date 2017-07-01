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
