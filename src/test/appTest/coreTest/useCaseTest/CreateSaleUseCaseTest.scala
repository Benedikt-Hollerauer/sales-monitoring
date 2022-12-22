package appTest.coreTest.useCaseTest

import app.boundary.input.CreateSaleInput
import app.boundary.repository.SaleRepository
import app.core.entity.SaleEntity
import app.core.useCase.CreateSaleUseCase
import app.error.inputError.CreateSaleInputError
import app.error.repositoryError.{RepositoryError, SaleRepositoryError}
import app.error.useCaseError.CreateSaleUseCaseError
import app.error.valueError.TitleValueError
import appTest.mock.{MockThrowable, StringMock}
import appTest.mock.entityMock.SaleEntityMock
import appTest.mock.inputMock.CreateSaleInputMock
import appTest.mock.repositoryMock.{SaleRepositoryMock, SaleRepositorySaveSaleToRepositoryFailureMock}
import zio.test.Assertion.*
import zio.test.*
import zio.*

import scala.util.control

object CreateSaleUseCaseTest extends ZIOSpecDefault:

    def spec =
        suite(
            CreateSaleUseCase.toString
        )(
            suite(
                ".createValidateSaveGetSale should return"
            )(
                test(
                    SaleEntity.toString
                )(
                    for
                        createSaleUseCase <- CreateSaleUseCase.from(
                            input = new CreateSaleInputMock,
                            saleRepository = SaleRepositoryMock()
                        )
                        useCaseResult <- createSaleUseCase.createValidateSaveGetSale
                    yield assertTrue(useCaseResult == SaleEntityMock)
                ),

                test(
                    CreateSaleUseCaseError.InputFailure(
                        CreateSaleInputError.SaleTitleConstructionFailed(
                            TitleValueError.TitleIsToShort(
                                StringMock.toShortString
                            )
                        )
                    ).toString
                )(
                    for
                        createSaleUseCase <- CreateSaleUseCase.from(
                            input = CreateSaleInputMock.toShortTitleFailureMock,
                            saleRepository = SaleRepositoryMock()
                        )
                        useCaseResult <- createSaleUseCase.createValidateSaveGetSale.cause
                        expected <- ZIO.fail(
                            CreateSaleUseCaseError.InputFailure(
                                CreateSaleInputError.SaleTitleConstructionFailed(
                                    TitleValueError.TitleIsToShort(
                                        StringMock.toShortString
                                    )
                                )
                            )
                        ).cause
                    yield assertTrue(useCaseResult.contains(expected))
                ),

                test(
                    CreateSaleUseCaseError.SaleRepositoryFailure(
                        SaleRepositoryError.SaveSaleToRepositoryFailed(
                            RepositoryError.Failure(MockThrowable)
                        )
                    ).toString
                )(
                    for
                        createSaleUseCase <- CreateSaleUseCase.from(
                            input = new CreateSaleInputMock,
                            saleRepository = SaleRepositorySaveSaleToRepositoryFailureMock
                        )
                        useCaseResult <- createSaleUseCase.createValidateSaveGetSale.cause
                        expected <- ZIO.fail(
                            CreateSaleUseCaseError.SaleRepositoryFailure(
                                SaleRepositoryError.SaveSaleToRepositoryFailed(
                                    RepositoryError.Failure(MockThrowable)
                                )
                            )
                        ).cause
                    yield assertTrue(useCaseResult.contains(expected))
                )
            )
        )