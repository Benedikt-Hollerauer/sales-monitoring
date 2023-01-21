package coreTest.useCaseTest

import boundary.input.CreateSaleInput
import boundary.repository.SaleRepository
import core.entity.SaleEntity
import core.useCase.CreateSaleUseCase
import error.inputError.CreateSaleInputError
import error.repositoryError.{RepositoryError, SaleRepositoryError}
import error.useCaseError.CreateSaleUseCaseError
import error.valueError.TitleValueError
import mock.{MockThrowable, StringMock}
import mock.entityMock.SaleEntityMock
import mock.inputMock.CreateSaleInputMock
import mock.repositoryMock.{SaleRepositoryMock, SaleRepositorySaveSaleToRepositoryFailureMock}
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