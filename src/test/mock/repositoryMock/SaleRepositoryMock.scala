package mock.repositoryMock

import core.entity.SaleEntity
import boundary.repository.SaleRepository
import core.value.{AmountValue, DescriptionValue, PlatformValue, TitleValue}
import error.repositoryError.SaleRepositoryError
import error.repositoryError.RepositoryError
import mock.MockThrowable
import mock.entityMock.SaleEntityMock
import zio.*

import java.time.LocalDate

case class SaleRepositoryMock() extends SaleRepository:

    override def saveSaleToRepository(saleEntity: SaleEntity): IO[SaleRepositoryError, Unit] = ZIO.succeed(())
    override def findLatestSalesByAmount(amount: AmountValue): IO[SaleRepositoryError, NonEmptyChunk[SaleEntity]] = ZIO.succeed(NonEmptyChunk(SaleEntityMock, SaleEntityMock))
    override def searchSalesByTitle(title: TitleValue): IO[SaleRepositoryError, NonEmptyChunk[SaleEntity]] = ZIO.succeed(NonEmptyChunk(SaleEntityMock, SaleEntityMock))
    override def searchSalesByDateSpan(dateSpan: (LocalDate, LocalDate)): IO[SaleRepositoryError, NonEmptyChunk[SaleEntity]] = ZIO.succeed(NonEmptyChunk(SaleEntityMock, SaleEntityMock))
    override def searchSalesByDescription(description: DescriptionValue): IO[SaleRepositoryError, NonEmptyChunk[SaleEntity]] = ZIO.succeed(NonEmptyChunk(SaleEntityMock, SaleEntityMock))
    override def searchSalesByPlatform(platform: PlatformValue): IO[SaleRepositoryError, NonEmptyChunk[SaleEntity]] = ZIO.succeed(NonEmptyChunk(SaleEntityMock, SaleEntityMock))

object SaleRepositorySaveSaleToRepositoryFailureMock extends SaleRepositoryMock {
    override def saveSaleToRepository(saleEntity: SaleEntity): IO[SaleRepositoryError, Unit] = ZIO.fail(SaleRepositoryError.SaveSaleToRepositoryFailed(RepositoryError.Failure(MockThrowable)))
}

object SaleRepositoryFindLatestSalesByAmountFailureMock extends SaleRepositoryMock {
    override def findLatestSalesByAmount(amount: AmountValue): IO[SaleRepositoryError, NonEmptyChunk[SaleEntity]] = ZIO.fail(SaleRepositoryError.FindLatestSalesByAmountFailed(RepositoryError.Failure(MockThrowable)))
}

object SaleRepositorySearchSalesByPlatformFailureMock extends SaleRepositoryMock {
    override def searchSalesByPlatform(platform: PlatformValue): IO[SaleRepositoryError, NonEmptyChunk[SaleEntity]] = ZIO.fail(SaleRepositoryError.SearchSalesByPlatformFailed(RepositoryError.Failure(MockThrowable)))
}

object SaleRepositorySearchSalesByDateSpanFailureMock extends SaleRepositoryMock {
    override def searchSalesByDateSpan(dateSpan: (LocalDate, LocalDate)): IO[SaleRepositoryError, NonEmptyChunk[SaleEntity]] = ZIO.fail(SaleRepositoryError.SearchSalesByDateSpanFailed(RepositoryError.Failure(MockThrowable)))
}