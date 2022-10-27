package mock.repositoryMock

import core.entity.SaleEntity
import boundary.repository.SaleRepository
import core.value.{AmountValue, TitleValue}
import error.repositoryError.SaleRepositoryError
import error.repositoryError.RepositoryError
import mock.MockThrowable
import mock.entityMock.SaleEntityMock
import zio.*

object SaleRepositoryMock extends SaleRepository:

    override def saveSaleToRepository(saleEntity: SaleEntity): IO[SaleRepositoryError, Unit] = ZIO.succeed(())

    override def findLatestSalesByAmount(amount: AmountValue): IO[SaleRepositoryError, NonEmptyChunk[SaleEntity]] = ZIO.succeed(NonEmptyChunk(SaleEntityMock, SaleEntityMock))

object SaleRepositoryFailureMock extends SaleRepository:

    override def saveSaleToRepository(saleEntity: SaleEntity): IO[SaleRepositoryError, Unit] = ZIO.fail(SaleRepositoryError.SaveSaleToRepositoryFailed(RepositoryError.Failure(MockThrowable)))

    override def findLatestSalesByAmount(amount: AmountValue): IO[SaleRepositoryError, NonEmptyChunk[SaleEntity]] = ZIO.fail(SaleRepositoryError.FindLatestSalesByAmountFailed(RepositoryError.Failure(MockThrowable)))