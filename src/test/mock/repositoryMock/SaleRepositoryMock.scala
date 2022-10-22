package mock.repositoryMock

import boundary.repository.SaleRepository
import zio.*

object SaleRepositoryMock extends SaleRepository:

    override def saveSaleToRepository(sale: SaleEntity): IO[SaleRepositoryError, Unit] =