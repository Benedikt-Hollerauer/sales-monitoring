package coreTest.useCaseTest

import core.useCase.CreateSaleUseCase
import core.entity.SaleEntity
import error.useCaseError.CreateSaleUseCaseError
import error.inputError.CreateSaleInputError
import error.valueError.TitleValueError
import error.repositoryError.SaleRepositoryError
import error.repositoryError.RepositoryError
import mock.entityMock.{SaleEntityMock, SaleEntityToShortTitleFailureMock}
import mock.inputMock.{CreateSaleInputMock, CreateSaleInputToShortTitleFailureMock}
import mock.repositoryMock.{SaleRepositoryMock, SaleRepositorySaveSaleToRepositoryFailureMock}
import mock.MockThrowable
import zio.test.Assertion.*
import zio.test.*
import zio.*

import scala.util.control

object CreateSaleUseCaseTest extends ZIOSpecDefault:

    def spec =
        suite("CreateSaleUseCase test")(
            suite("CreateSaleUseCase.createValidateSaveGetSale should return")(
                test("SaleEntity when correct parameters are provided")(
                    for
                        createSaleUseCase <- CreateSaleUseCase.from(
                            input = CreateSaleInputMock,
                            saleRepository = SaleRepositoryMock()
                        )
                        useCaseResult <- createSaleUseCase.createValidateSaveGetSale
                    yield assertTrue(useCaseResult == SaleEntityMock)
                ),

                test("CreateSaleUseCaseError.InputFailed(CreateSaleInputError.SaleTitleConstructionFailed(TitleValueError.TitleIsToShort) when a to short CreateSaleInput.saleTitle is provided")(
                    for
                        createSaleUseCase <- CreateSaleUseCase.from(
                            input = CreateSaleInputToShortTitleFailureMock,
                            saleRepository = SaleRepositoryMock()
                        )
                        useCaseResult <- createSaleUseCase.createValidateSaveGetSale.cause
                        expected <- ZIO.fail(
                            CreateSaleUseCaseError.InputFailure(CreateSaleInputError.SaleTitleConstructionFailed(TitleValueError.TitleIsToShort("")))
                        ).cause
                    yield assertTrue(useCaseResult == expected)
                ),

                test("CreateSaleUseCaseError.SaleRepositoryFailure(SaleRepositoryError.SaveSaleToRepositoryFailed(RepositoryError.Failure))) when a failure occurred in the SaleRepository")(
                    for
                        createSaleUseCase <- CreateSaleUseCase.from(
                            input = CreateSaleInputMock,
                            saleRepository = SaleRepositorySaveSaleToRepositoryFailureMock
                        )
                        useCaseResult <- createSaleUseCase.createValidateSaveGetSale.cause
                        expected <- ZIO.fail(
                            CreateSaleUseCaseError.SaleRepositoryFailure(SaleRepositoryError.SaveSaleToRepositoryFailed(RepositoryError.Failure(MockThrowable)))
                        ).cause
                    yield assertTrue(useCaseResult == expected)
                )
            )
        )