package controllers

import models.User
import repo.UserRepository
import play.api.Logger

import scala.concurrent.Future


/**
  * Created by knoldus on 30/6/17.
  */
class UserController @Inject()(userRepository: UserRepository, val messagesApi: MessagesApi) extends Controller with I18nSupport {

  val logger = Logger(this.getClass())

  def list() = Action.async {
    userRepository.getAll().map { res =>
      logger.info("User list: " + res)
      Ok(successResponse(Json.toJson(res), Messages("user.success.userList")))
    }
  }

  def create() = Action.async(parse.json) { request =>
    logger.info("User Json ===> " + request.body)
    request.body.validate[User].fold(error => Future.successful(BadRequest(JsError.toJson(error))), { user =>
      userRepository.insert(user).map { createdUserId =>
        Ok(successResponse(Json.toJson(Map("id" -> createdUserId)), Messages("user.success.created")))
      }
    })
  }

  def delete(id: Int) = Action.async { request =>
    userRepository.delete(id).map { _ =>
      Ok(successResponse(Json.toJson("{}"), Messages("user.success.deleted")))
    }
  }

  def edit(id: Int): Action[AnyContent] = Action.async { request =>
    userRepository.getById(id).map { userOpt =>
      userOpt.fold(Ok(errorResponse(Json.toJson("{}"), Messages("user.error.userNotExist"))))(user => Ok(
        successResponse(Json.toJson(user), Messages("user.success.user"))))
    }
  }

  private def errorResponse(data: JsValue, message: String) = {
    obj("status" -> ERROR, "data" -> data, "msg" -> message)
  }

  def update = Action.async(parse.json) { request =>
    logger.info("User Json ===> " + request.body)
    request.body.validate[User].fold(error => Future.successful(BadRequest(JsError.toJson(error))), { user =>
      userRepository.update(user).map { res => Ok(successResponse(Json.toJson("{}"), Messages("user.success.updated"))) }
    })
  }

  private def successResponse(data: JsValue, message: String) = {
    obj("status" -> SUCCESS, "data" -> data, "msg" -> message)
  }



}
