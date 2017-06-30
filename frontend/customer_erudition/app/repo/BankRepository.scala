package repo

import javax.inject.{Inject, Singleton}

import models.Bank
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile

import scala.concurrent.Future

class BankRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends BankTable with HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  def insert(bank: Bank): Future[Int] = db.run {
    bankTableQueryInc += bank
  }

  def insertAll(banks: List[Bank]): Future[Seq[Int]] = db.run {
    bankTableQueryInc ++= banks
  }

  def update(bank: Bank): Future[Int] = db.run {
    bankTableQuery.filter(_.id === bank.id).update(bank)
  }

  def delete(id: Int): Future[Int] = db.run {
    bankTableQuery.filter(_.id === id).delete
  }

  def getAll(): Future[List[Bank]] = db.run {
    bankTableQuery.to[List].result
  }

  def getById(id: Int): Future[Option[Bank]] = db.run {
    bankTableQuery.filter(_.id === id).result.headOption
  }

  def ddl = bankTableQuery.schema

}

private[repo] trait BankTable {
  self: HasDatabaseConfigProvider[JdbcProfile] =>
  import driver.api._

  lazy protected val bankTableQuery = TableQuery[BankTable]
  lazy protected val bankTableQueryInc = bankTableQuery returning bankTableQuery.map(_.id)

  private[BankTable] class BankTable(tag: Tag) extends Table[Bank](tag, "bank") {
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    val name: Rep[String] = column[String]("name", O.SqlType("VARCHAR(50)"))
    val branch: Rep[String] = column[String]("password", O.SqlType("VARCHAR(50)"))
    val ifscCode: Rep[String] = column[String]("ifsc_code", O.SqlType("VARCHAR(20)"))
    val reference: Rep[Int] = column[Int]("reference")

    def * = (id, name, branch, ifscCode) <> (Bank.tupled, Bank.unapply)

  }

}
