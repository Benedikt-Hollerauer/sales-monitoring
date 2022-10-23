package mock.repositoryMock

import core.entity.SaleEntity
import boundary.repository.SaleRepository
import core.value.TitleValue
import error.repositoryError.SaleRepositoryError
import mock.MockThrowable
import zio.*

object SaleRepositoryMock extends SaleRepository:

    override def saveSaleToRepository(saleEntity: SaleEntity): IO[SaleRepositoryError, Unit] = ZIO.succeed(())

object SaleRepositoryFailureMock extends SaleRepository:

    override def saveSaleToRepository(saleEntity: SaleEntity): IO[SaleRepositoryError, Unit] = ZIO.fail(SaleRepositoryError.SaveSaleToRepositoryFailed(MockThrowable))