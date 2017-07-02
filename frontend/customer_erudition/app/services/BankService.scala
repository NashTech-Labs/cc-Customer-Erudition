package services

import com.google.inject.Inject
import models.Bank
import repo.BankRepository

import scala.concurrent.Future


class BankService @Inject()(bankRepo: BankRepository) {

  def create(bank: Bank): Future[Int] = {
    bankRepo.insert(bank)
  }

  def allBanks: Future[List[Bank]] = {
    bankRepo.getAll()
  }

}
