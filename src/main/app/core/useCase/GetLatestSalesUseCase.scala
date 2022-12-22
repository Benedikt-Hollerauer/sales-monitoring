package app.core.useCase

import app.boundary.input.GetLatestSalesInput
import app.boundary.repository.SaleRepository
import app.core.entity.SaleEntity
import app.error.inputError.GetLatestSalesInputError
import app.error.useCaseError.GetLatestSalesUseCaseError
import zio.*

case class GetLatestSalesUseCase private(
    input: GetLatestSalesInput,
    saleRepository: SaleRepository
):
    def getValidateLatestSales: IO[GetLatestSalesUseCaseError, NonEmptyChunk[SaleEntity]] =
        for
            amountValue <- input.amountOfSales
                  .catchAll(amountValueError =>
                        ZIO.fail(GetLatestSalesUseCaseError.InputFailure(GetLatestSalesInputError.AmountOfSalesConstructionFailed(amountValueError)))
                  )
            latestSales <- saleRepository.findLatestSalesByAmount(amountValue)
                .catchAll(saleRepositoryError =>
                    ZIO.fail(GetLatestSalesUseCaseError.SaleRepositoryFailure(saleRepositoryError))
                )
        yield latestSales

object GetLatestSalesUseCase:

    def from(
        input: GetLatestSalesInput,
        saleRepository: SaleRepository
    ): UIO[GetLatestSalesUseCase] =
        ZIO.succeed(
            GetLatestSalesUseCase(
                input,
                saleRepository
            )
        )