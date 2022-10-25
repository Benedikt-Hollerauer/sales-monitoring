package core.useCase

import boundary.repository.SaleRepository
import boundary.input.GetLatestSalesInput
import core.entity.SaleEntity
import error.useCaseError.GetLatestSalesUseCaseError
import error.inputError.GetLatestSalesInputError
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