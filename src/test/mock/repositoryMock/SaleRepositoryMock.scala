package mock.repositoryMock

import core.entity.SaleEntity
import boundary.repository.SaleRepository
import zio.*

object SaleRepositoryMock extends SaleRepository:

    override def saveSaleToRepository(sale: SaleEntity): IO[SaleRepositoryError, Unit] =