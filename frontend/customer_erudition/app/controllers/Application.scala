package controllers

import javax.inject.Inject

import play.api.data.Form
import play.api.data.Forms.{nonEmptyText, tuple, email}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import repo.UserRepository

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
     formWithErrors => {
println("??????????")
       Future.successful(Ok)
     },
     data => {
       val (email, pass) = data
       println(">>>>>>>>" + data)
       userRepo.getByEmailAndPass(email, pass).map {
         case Some(user) => Ok
         case None => Ok
       }
     }
   )
  }



}
