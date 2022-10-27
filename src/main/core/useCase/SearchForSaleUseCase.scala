package core.useCase

import boundary.input.SearchForSaleInput
import boundary.repository.SaleRepository
import core.entity.SaleEntity
import error.useCaseError.SearchForSaleUseCaseError
import zio.*

case class SearchForSaleUseCase private(
    input: SearchForSaleInput,
    saleRepository: SaleRepository
):
    def searchValidateGetSales: IO[SearchForSaleUseCaseError, NonEmptyChunk[SaleEntity]]

object SearchForSaleUseCase:

    def from(
        input: SearchForSaleInput,
        saleRepository: SaleRepository
    ): UIO[SearchForSaleUseCase] =
        ZIO.succeed(
            SearchForSaleUseCase(
                input,
                saleRepository
            )
        )