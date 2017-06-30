package controllers

import models.Bank
import repo.BankRepository
import play.api.Logger

import scala.concurrent.Future

class BankController @Inject()(bankRepository: BankRepository, val messagesApi: MessagesApi) extends Controller with I18nSupport {

  val logger = Logger(this.getClass())

  def list() = Action.async {
    bankRepository.getAll().map { res =>
      logger.info("Bank list: " + res)
      Ok(successResponse(Json.toJson(res), Messages("bank.success.bankList")))
    }
  }

  def create() = Action.async(parse.json) { request =>
    logger.info("Bank Json ===> " + request.body)
    request.body.validate[Bank].fold(error => Future.successful(BadRequest(JsError.toJson(error))), { bank =>
      bankRepository.insert(bank).map { createdBankId =>
        Ok(successResponse(Json.toJson(Map("id" -> createdBankId)), Messages("bank.success.created")))
      }
    })
  }

  def delete(id: Int) = Action.async { request =>
    bankRepository.delete(id).map { _ =>
      Ok(successResponse(Json.toJson("{}"), Messages("bank.success.deleted")))
    }
  }

  def edit(id: Int): Action[AnyContent] = Action.async { request =>
    bankRepository.getById(id).map { bankOpt =>
      bankOpt.fold(Ok(errorResponse(Json.toJson("{}"), Messages("bank.error.bankNotExist"))))(bank => Ok(
        successResponse(Json.toJson(bank), Messages("bank.success.bank"))))
    }
  }

  private def errorResponse(data: JsValue, message: String) = {
    obj("status" -> ERROR, "data" -> data, "msg" -> message)
  }

  def update = Action.async(parse.json) { request =>
    logger.info("Bank Json ===> " + request.body)
    request.body.validate[Bank].fold(error => Future.successful(BadRequest(JsError.toJson(error))), { bank =>
      bankRepository.update(bank).map { res => Ok(successResponse(Json.toJson("{}"), Messages("bank.success.updated"))) }
    })
  }

  private def successResponse(data: JsValue, message: String) = {
    obj("status" -> SUCCESS, "data" -> data, "msg" -> message)
  }



}
