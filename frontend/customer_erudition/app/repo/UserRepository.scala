package repo

import javax.inject.{Inject, Singleton}

import models.User
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile

import scala.concurrent.Future

class UserRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends UserTable with HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  def insert(user: User): Future[Int] = db.run {
    userTableQueryInc += user
  }

  def insertAll(users: List[User]): Future[Seq[Int]] = db.run {
    userTableQueryInc ++= users
  }

  def update(user: User): Future[Int] = db.run {
    userTableQuery.filter(_.id === user.id).update(user)
  }

  def delete(id: Int): Future[Int] = db.run {
    userTableQuery.filter(_.id === id).delete
  }

  def getAll(): Future[List[User]] = db.run {
    userTableQuery.to[List].result
  }

  def getById(id: Int): Future[Option[User]] = db.run {
    userTableQuery.filter(_.id === id).result.headOption
  }

  def ddl = userTableQuery.schema

}

private[repo] trait UserTable {
  self: HasDatabaseConfigProvider[JdbcProfile] =>
  import driver.api._

  lazy protected val userTableQuery = TableQuery[UserTable]
  lazy protected val userTableQueryInc = userTableQuery returning userTableQuery.map(_.id)

  private[UserTable] class UserTable(tag: Tag) extends Table[User](tag, "user") {
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    val name: Rep[String] = column[String]("name", O.SqlType("VARCHAR(15)"))
    val password: Rep[String] = column[String]("password", O.SqlType("VARCHAR(16)"))
    val role: Rep[String] = column[String]("role", O.SqlType("VARCHAR(2)"))
    val reference: Rep[Int] = column[Int]("reference")

    def nameUnique = index("name_unique_key", name, unique = true)

    def * = (id, name, password, role, reference.?) <> (User.tupled, User.unapply)

  }

}
