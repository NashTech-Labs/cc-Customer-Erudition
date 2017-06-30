package models

case class User(id: Int, name: String, password: String, role:String, reference: Option[Int]=None)

case class Bank(id: Int, name: String, branch: String, ifscCode: String)

case class Agent(id: Int, name: String, createdBy: Int)

