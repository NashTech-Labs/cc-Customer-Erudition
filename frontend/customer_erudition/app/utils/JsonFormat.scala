package utils

import play.api.libs.json.Json
import models._

object JsonFormat {

  implicit val userJsonFormat = Json.format[User]
  implicit val bankJsonFormat = Json.format[Bank]
  implicit val agentJsonFormat = Json.format[Agent]

}
