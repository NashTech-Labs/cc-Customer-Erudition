package controllers

import com.google.inject.Inject
import models.Agent
import repo.AgentRepository
import play.api.Logger
import play.api.libs.json.{JsError, JsValue, Json}
import play.api.mvc.{AbstractController, ControllerComponents}
import utils.JsonFormat._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class AgentController @Inject()(cc: ControllerComponents, agentRepository: AgentRepository)
  extends AbstractController(cc) {

  val logger = Logger(this.getClass())

  def home(user: String) = Action.async {
    Future.successful(Ok(views.html.agentDashboard(user)))
  }

  def list() = Action.async {
    agentRepository.getAll().map { res =>
      logger.info("Agent list: " + res)
      Ok(successResponse(Json.toJson(res)))
    }
  }

  private def successResponse(data: JsValue) = {
    Json.obj("status" -> "SUCCESS", "data" -> data)
  }



}
