package controllers

import models.Bank
import repo.BankRepository
import com.google.inject.Inject
import play.api.Logger
import play.api.libs.json.{JsError, JsValue, Json}
import play.api.mvc.{AbstractController, ControllerComponents}
import utils.JsonFormat._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class BankController @Inject()(cc: ControllerComponents, bankRepository: BankRepository)
  extends AbstractController(cc) {

  val logger = Logger(this.getClass())

  def home(user: String) = Action.async {
    Future.successful(Ok(views.html.bankDashboard(user)))
  }

  def list() = Action.async {
    bankRepository.getAll().map { res =>
      logger.info("Bank list: " + res)
      Ok(successResponse(Json.toJson(res)))
    }
  }

  private def successResponse(data: JsValue) = {
    Json.obj("status" -> "SUCCESS", "data" -> data)
  }



}
