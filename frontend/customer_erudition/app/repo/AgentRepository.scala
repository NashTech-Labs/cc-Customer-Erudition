package repo

import javax.inject.{Inject, Singleton}

import models.Agent
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile

import scala.concurrent.Future

class AgentRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends AgentTable with UserTable with HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  def insert(agent: Agent): Future[Int] = db.run {
    agentTableQueryInc += agent
  }

  def insertAll(agents: List[Agent]): Future[Seq[Int]] = db.run {
    agentTableQueryInc ++= agents
  }

  def update(agent: Agent): Future[Int] = db.run {
    agentTableQuery.filter(_.id === agent.id).update(agent)
  }

  def delete(id: Int): Future[Int] = db.run {
    agentTableQuery.filter(_.id === id).delete
  }

  def getAll(): Future[List[Agent]] = db.run {
    agentTableQuery.to[List].result
  }

  def getById(id: Int): Future[Option[Agent]] = db.run {
    agentTableQuery.filter(_.id === id).result.headOption
  }

  /*def getDetails(): Future[List[Any]] = db.run {
    agentTableQuery.join(userTableQuery).on()
  }*/

  def ddl = agentTableQuery.schema

}

private[repo] trait AgentTable {
  self: HasDatabaseConfigProvider[JdbcProfile] =>
  import driver.api._

  lazy protected val agentTableQuery = TableQuery[AgentTable]
  lazy protected val agentTableQueryInc = agentTableQuery returning agentTableQuery.map(_.id)

  private[AgentTable] class AgentTable(tag: Tag) extends Table[Agent](tag, "agent") {
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    val name: Rep[String] = column[String]("name", O.SqlType("VARCHAR(20)"))
    val createdBy: Rep[Int] = column[Int]("created_by", O.SqlType("NUMBER(1000)"))

    def * = (id, name, createdBy) <> (Agent.tupled, Agent.unapply)

  }

}


