package app.core.useCase

import app.boundary.input.CreateSaleInput
import app.boundary.repository.SaleRepository
import app.core.entity.SaleEntity
import app.error.inputError.CreateSaleInputError
import app.error.repositoryError.SaleRepositoryError
import app.error.useCaseError.CreateSaleUseCaseError
import zio.*

case class CreateSaleUseCase private(
    input: CreateSaleInput,
    saleRepository: SaleRepository
):
    def createValidateSaveGetSale: IO[CreateSaleUseCaseError, SaleEntity] =
        for
            _ <- input.saleEntity.title
                      .catchAll(titleValueError =>
                    ZIO.fail(CreateSaleUseCaseError.InputFailure(CreateSaleInputError.SaleTitleConstructionFailed(titleValueError)))
                )
            _ <- saleRepository
                .saveSaleToRepository(input.saleEntity)
                .catchAll(saleRepositoryError =>
                    ZIO.fail(CreateSaleUseCaseError.SaleRepositoryFailure(saleRepositoryError))
                )
        yield input.saleEntity

object CreateSaleUseCase:

    def from(
        input: CreateSaleInput,
        saleRepository: SaleRepository
    ): UIO[CreateSaleUseCase] =
        ZIO.succeed(
            CreateSaleUseCase(
                input,
                saleRepository
            )
        )