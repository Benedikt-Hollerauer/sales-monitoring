package core.useCase

import boundary.input.SearchForSaleInput
import boundary.repository.SaleRepository
import core.entity.SaleEntity
import error.useCaseError.SearchForSaleUseCaseError
import error.inputError.SearchForSaleInputError
import zio.*

case class SearchForSaleUseCase private(
    input: SearchForSaleInput,
    saleRepository: SaleRepository
):
    def searchValidateGetSales: IO[SearchForSaleUseCaseError, NonEmptyChunk[SaleEntity]] =
        for
            saleTitle <- input.saleTitle
                              .catchAll(titleValueError =>
                                  ZIO.fail(SearchForSaleUseCaseError.InputFailure(SearchForSaleInputError.SaleTitleConstructionFailed(titleValueError)))
                              )
            saleDateSpan <- input.saleDateSpan
                                 .catchAll(localDateException =>
                                     ZIO.fail(SearchForSaleUseCaseError.InputFailure(SearchForSaleInputError.SaleDateSpanConstructionFailed(localDateException)))
                                 )
            saleDescription <- input.saleDescription
                                    .catchAll(descriptionValueError =>
                                        ZIO.fail(SearchForSaleUseCaseError.InputFailure(SearchForSaleInputError.SaleDescriptionConstructionFailed(descriptionValueError)))
                                    )
            salesPlatform = input.salesPlatform
        yield ???

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