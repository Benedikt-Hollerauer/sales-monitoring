package boundary.repository

import core.entity.SaleEntity
import core.value.AmountValue
import error.repositoryError.SaleRepositoryError
import zio.*

trait SaleRepository:

    def saveSaleToRepository(saleEntity: SaleEntity): IO[SaleRepositoryError, Unit]

    def findLatestSalesByAmount(amount: AmountValue): IO[SaleRepositoryError, NonEmptyChunk[SaleEntity]]

object SaleRepository:

    def saveSaleToRepository(saleEntity: SaleEntity) =
        ZIO.serviceWithZIO[SaleRepository](_.saveSaleToRepository(saleEntity))

    def findLatestSalesByAmount(amount: AmountValue) =
        ZIO.serviceWithZIO[SaleRepository](_.findLatestSalesByAmount(amount))