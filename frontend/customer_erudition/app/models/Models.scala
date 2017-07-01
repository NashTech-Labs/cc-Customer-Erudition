package models

case class User(id: Int, email: String, name: String, password: String, role:String, reference: Option[Int]=None)

case class Bank(name: String, branch: String, ifscCode: String, id: Int = 0)

case class Agent(id: Int, name: String, createdBy: Int)

