package controllers

import javax.inject.Inject

import play.api.data.Form
import play.api.data.Forms.{email, nonEmptyText, tuple}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  val loginForm: Form[(String, String)] = Form(
    tuple(
      "username" -> email,
      "password" -> nonEmptyText))

  def index() = Action.async {
    Future.successful(Ok(views.html.index()))
  }

  def authenticate() = Action { implicit request: Request[AnyContent] =>
   loginForm.bindFromRequest.fold(
     formWithErrors => Ok,
     data => Ok
   )
  }



}
