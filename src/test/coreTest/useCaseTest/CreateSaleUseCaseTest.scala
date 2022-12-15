package coreTest.useCaseTest

import boundary.input.CreateSaleInput
import boundary.repository.SaleRepository
import core.useCase.CreateSaleUseCase
import core.entity.SaleEntity
import error.useCaseError.CreateSaleUseCaseError
import error.inputError.CreateSaleInputError
import error.valueError.TitleValueError
import error.repositoryError.SaleRepositoryError
import error.repositoryError.RepositoryError
import mock.entityMock.SaleEntityMock
import mock.inputMock.CreateSaleInputMock
import mock.repositoryMock.{SaleRepositoryMock, SaleRepositorySaveSaleToRepositoryFailureMock}
import mock.MockThrowable
import zio.test.Assertion.*
import zio.test.*
import zio.*

import scala.util.control

object CreateSaleUseCaseTest extends ZIOSpecDefault:

    def spec =
        suite(s"${CreateSaleUseCase.getClass.getSimpleName}")(
            suite(".createValidateSaveGetSale should return")(
                test(s"${SaleEntity.getClass.getSimpleName}")(
                    for
                        createSaleUseCase <- CreateSaleUseCase.from(
                            input = new CreateSaleInputMock,
                            saleRepository = SaleRepositoryMock()
                        )
                        useCaseResult <- createSaleUseCase.createValidateSaveGetSale
                    yield assertTrue(useCaseResult == SaleEntityMock)
                ),

                test(s"${CreateSaleUseCaseError.InputFailure.getClass.getSimpleName}(${CreateSaleInputError.SaleTitleConstructionFailed.getClass.getSimpleName}(${TitleValueError.TitleIsToShort.getClass.getSimpleName})")(
                    for
                        createSaleUseCase <- CreateSaleUseCase.from(
                            input = CreateSaleInputMock.toShortTitleFailureMock,
                            saleRepository = SaleRepositoryMock()
                        )
                        useCaseResult <- createSaleUseCase.createValidateSaveGetSale.cause
                        expected <- ZIO.fail(
                            CreateSaleUseCaseError.InputFailure(CreateSaleInputError.SaleTitleConstructionFailed(TitleValueError.TitleIsToShort("")))
                        ).cause
                    yield assertTrue(useCaseResult.contains(expected))
                ),

                test(s"${CreateSaleUseCaseError.SaleRepositoryFailure.getClass.getSimpleName}(${SaleRepositoryError.SaveSaleToRepositoryFailed.getClass.getSimpleName}(${RepositoryError.Failure.getClass.getSimpleName})))")(
                    for
                        createSaleUseCase <- CreateSaleUseCase.from(
                            input = new CreateSaleInputMock,
                            saleRepository = SaleRepositorySaveSaleToRepositoryFailureMock
                        )
                        useCaseResult <- createSaleUseCase.createValidateSaveGetSale.cause
                        expected <- ZIO.fail(
                            CreateSaleUseCaseError.SaleRepositoryFailure(SaleRepositoryError.SaveSaleToRepositoryFailed(RepositoryError.Failure(MockThrowable)))
                        ).cause
                    yield assertTrue(useCaseResult.contains(expected))
                )
            )
        )