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

  def list() = Action.async {
    bankRepository.getAll().map { res =>
      logger.info("Bank list: " + res)
      Ok(successResponse(Json.toJson(res)))
    }
  }

  def create() = Action.async(parse.json) { request =>
    logger.info("Bank Json ===> " + request.body)
    request.body.validate[Bank].fold(error => Future.successful(BadRequest(JsError.toJson(error))), { bank =>
      bankRepository.insert(bank).map { createdBankId =>
        Ok(successResponse(Json.toJson(Map("id" -> createdBankId))))
      }
    })
  }

  /*def delete(id: Int) = Action.async { request =>
    bankRepository.delete(id).map { _ =>
      Ok(successResponse(Json.toJson("{}"), Messages("bank.success.deleted")))
    }
  }

  def edit(id: Int): Action[AnyContent] = Action.async { request =>
    bankRepository.getById(id).map { bankOpt =>
      bankOpt.fold(Ok(errorResponse(Json.toJson("{}"), Messages("bank.error.bankNotExist"))))(bank => Ok(
        successResponse(Json.toJson(bank), Messages("bank.success.bank"))))
    }
  }*/

  def update = Action.async(parse.json) { request =>
    logger.info("Bank Json ===> " + request.body)
    request.body.validate[Bank].fold(error => Future.successful(BadRequest(JsError.toJson(error))), { bank =>
      bankRepository.update(bank).map { res => Ok(successResponse(Json.toJson("{}"))) }
    })
  }

  private def errorResponse(data: JsValue) = {
    Json.obj("status" -> "ERROR", "data" -> data)
  }


  private def successResponse(data: JsValue) = {
    Json.obj("status" -> "SUCCESS", "data" -> data)
  }



}
