package controllers

import javax.inject.Inject

import play.api.data.Form
import play.api.data.Forms.{nonEmptyText, tuple, email}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import repo.UserRepository
import constants.Constants._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class Application @Inject()(cc: ControllerComponents, userRepo: UserRepository) extends AbstractController(cc) {

  val loginForm: Form[(String, String)] = Form(
    tuple(
      "email" -> email,
      "pwd" -> nonEmptyText))

  def index() = Action.async {
    Future.successful(Ok(views.html.index()))
  }

  def authenticate() = Action.async { implicit request: Request[AnyContent] =>
   loginForm.bindFromRequest.fold(
     _ => Future.successful(Ok),
     loginData => {
       val (email, pass) = loginData
       userRepo.getByEmailAndPass(email, pass).map {
         case Some(user) =>
           user.role match {
             case SUPER => Redirect(routes.UserController.home(user.name))
             case BANK => Redirect(routes.BankController.home(user.name))
             case AGENT => Redirect(routes.AgentController.home(user.name))
           }
         case None => Redirect(routes.Application.index())
       }
     }
   )
  }

  def logout = Action {
    Redirect(routes.Application.index())
  }

}
