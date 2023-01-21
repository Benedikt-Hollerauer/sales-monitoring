package boundary.repository

import core.entity.SaleEntity
import core.value.{AmountValue, DescriptionValue, PlatformValue, TitleValue}
import error.repositoryError.SaleRepositoryError
import zio.*

import java.time.LocalDate

trait SaleRepository:

    def saveSaleToRepository(saleEntity: SaleEntity): IO[SaleRepositoryError, Unit]
    def findLatestSalesByAmount(amount: AmountValue): IO[SaleRepositoryError, NonEmptyChunk[SaleEntity]]
    def searchSalesByTitle(title: TitleValue): IO[SaleRepositoryError, NonEmptyChunk[SaleEntity]]
    def searchSalesByDateSpan(dateSpan: (LocalDate, LocalDate)): IO[SaleRepositoryError, NonEmptyChunk[SaleEntity]]
    def searchSalesByDescription(description: DescriptionValue): IO[SaleRepositoryError, NonEmptyChunk[SaleEntity]]
    def searchSalesByPlatform(platform: PlatformValue): IO[SaleRepositoryError, NonEmptyChunk[SaleEntity]]

object SaleRepository:

    def saveSaleToRepository(saleEntity: SaleEntity) =
        ZIO.serviceWithZIO[SaleRepository](_.saveSaleToRepository(saleEntity))
    def findLatestSalesByAmount(amount: AmountValue) =
        ZIO.serviceWithZIO[SaleRepository](_.findLatestSalesByAmount(amount))
    def searchSalesByTitle(title: TitleValue) =
        ZIO.serviceWithZIO[SaleRepository](_.searchSalesByTitle(title))
    def searchSalesByDateSpan(dateSpan: (LocalDate, LocalDate)) =
        ZIO.serviceWithZIO[SaleRepository](_.searchSalesByDateSpan(dateSpan))
    def searchSalesByDescription(description: DescriptionValue) =
        ZIO.serviceWithZIO[SaleRepository](_.searchSalesByDescription(description))
    def searchSalesByPlatform(platform: PlatformValue) =
        ZIO.serviceWithZIO[SaleRepository](_.searchSalesByPlatform(platform))