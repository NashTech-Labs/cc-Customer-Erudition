package controllers

import repo.UserRepository
import com.google.inject.Inject
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms.{nonEmptyText, tuple}
import play.api.libs.json.{Json}
import play.api.mvc.{AbstractController, ControllerComponents}
import services.BankService
import utils.JsonFormat._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class UserController @Inject()(cc: ControllerComponents, userRepository: UserRepository, bankService: BankService)
  extends AbstractController(cc) {

  val logger = Logger(this.getClass())

  def home(user: String) = Action.async {
      Future.successful(Ok(views.html.superUserDashboard(user)))
  }

  def bankList = Action.async {
    bankService.allBanks.map { res =>
      Ok(Json.toJson(res))
    }
  }

  def bankForm = Action.async {
    Future.successful(Ok(views.html.newBankForm()))
  }

  val newBankForm = Form(
    tuple(
      "name" -> nonEmptyText,
      "branch" -> nonEmptyText,
      "ifsc" -> nonEmptyText))

  /*def newBankForm = Action.async { implicit request =>
    bankForm.bindFromRequest.fold(
      _ => Future.successful(Ok),
      data => {
       val bank = Bank(data._1, data._2, data._3)
        bankService.create(bank).map(_ => Ok)
      }
    )
  }*/

}
