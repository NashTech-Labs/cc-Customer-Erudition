package controllers

import models.Agent
import repo.AgentRepository
import play.api.Logger

import scala.concurrent.Future

class AgentController @Inject()(agentRepository: AgentRepository, val messagesApi: MessagesApi) extends Controller with I18nSupport {

  val logger = Logger(this.getClass())

  def list() = Action.async {
    agentRepository.getAll().map { res =>
      logger.info("Agent list: " + res)
      Ok(successResponse(Json.toJson(res), Messages("agent.success.agentList")))
    }
  }

  def create() = Action.async(parse.json) { request =>
    logger.info("Agent Json ===> " + request.body)
    request.body.validate[Agent].fold(error => Future.successful(BadRequest(JsError.toJson(error))), { agent =>
      agentRepository.insert(agent).map { createdAgentId =>
        Ok(successResponse(Json.toJson(Map("id" -> createdAgentId)), Messages("agent.success.created")))
      }
    })
  }

  def delete(id: Int) = Action.async { request =>
    agentRepository.delete(id).map { _ =>
      Ok(successResponse(Json.toJson("{}"), Messages("agent.success.deleted")))
    }
  }

  def edit(id: Int): Action[AnyContent] = Action.async { request =>
    agentRepository.getById(id).map { agentOpt =>
      agentOpt.fold(Ok(errorResponse(Json.toJson("{}"), Messages("agent.error.agentNotExist"))))(agent => Ok(
        successResponse(Json.toJson(agent), Messages("agent.success.agent"))))
    }
  }

  private def errorResponse(data: JsValue, message: String) = {
    obj("status" -> ERROR, "data" -> data, "msg" -> message)
  }

  def update = Action.async(parse.json) { request =>
    logger.info("Agent Json ===> " + request.body)
    request.body.validate[Agent].fold(error => Future.successful(BadRequest(JsError.toJson(error))), { agent =>
      agentRepository.update(agent).map { res => Ok(successResponse(Json.toJson("{}"), Messages("agent.success.updated"))) }
    })
  }

  private def successResponse(data: JsValue, message: String) = {
    obj("status" -> SUCCESS, "data" -> data, "msg" -> message)
  }



}
