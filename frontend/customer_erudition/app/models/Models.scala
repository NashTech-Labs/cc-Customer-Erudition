package models

case class User(id: Int, name: String, password: String, role:String, reference: Option[String]=None)
