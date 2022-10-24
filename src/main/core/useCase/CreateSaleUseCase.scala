package core.useCase

import boundary.input.CreateSaleInput
import boundary.repository.SaleRepository
import core.entity.SaleEntity
import error.inputError.CreateSaleInputError
import error.useCaseError.CreateSaleUseCaseError
import error.repositoryError.SaleRepositoryError
import zio.*

case class CreateSaleUseCase private(
    input: CreateSaleInput,
    saleRepository: SaleRepository
):
    def createValidateSaveGetSale: IO[CreateSaleUseCaseError, SaleEntity] =
        for
            _ <- input.saleEntity.saleTitle
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