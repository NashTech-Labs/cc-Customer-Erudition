package controllers

import models.User
import repo.UserRepository
import com.google.inject.Inject
import play.api.Logger
import play.api.libs.json.{JsError, JsValue, Json}
import play.api.mvc.{AbstractController, ControllerComponents}
import utils.JsonFormat._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class UserController @Inject()(cc: ControllerComponents, userRepository: UserRepository)
  extends AbstractController(cc) {

  val logger = Logger(this.getClass())

  def list() = Action.async {
    userRepository.getAll().map { res =>
      logger.info("User list: " + res)
      Ok(successResponse(Json.toJson(res)))
    }
  }

  def create() = Action.async(parse.json) { request =>
    logger.info("User Json ===> " + request.body)
    request.body.validate[User].fold(error => Future.successful(BadRequest(JsError.toJson(error))), { user =>
      userRepository.insert(user).map { createdUserId =>
        Ok(successResponse(Json.toJson(Map("id" -> createdUserId))))
      }
    })
  }

  /*def delete(id: Int) = Action.async { request =>
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
  }*/

  private def successResponse(data: JsValue) = {
    Json.obj("status" -> "SUCCESS", "data" -> data)
  }

}
