package appTest.coreTest.useCaseTest

import app.boundary.input.CreateSaleInput
import app.boundary.repository.SaleRepository
import app.core.entity.SaleEntity
import app.core.useCase.GetLatestSalesUseCase
import app.error.inputError.GetLatestSalesInputError
import app.error.repositoryError.{RepositoryError, SaleRepositoryError}
import app.error.useCaseError.GetLatestSalesUseCaseError
import app.error.valueError.AmountValueError
import appTest.mock.MockThrowable
import appTest.mock.entityMock.SaleEntityMock
import appTest.mock.inputMock.GetLatestSalesInputMock
import appTest.mock.repositoryMock.{SaleRepositoryFindLatestSalesByAmountFailureMock, SaleRepositoryMock}
import appTest.mock.inputMock.{GetLatestSalesNegativeAmountOfSalesFailureInputMock}
import zio.test.*
import zio.*

object GetLatestSalesUseCaseTest extends ZIOSpecDefault:

    def spec =
        suite(
            GetLatestSalesUseCase.toString
        )(
            suite(
                ".getValidateLatestSales should return"
            )(
                test(
                    "NonEmptyChunk[SaleEntity]"
                )(
                    for
                        getLatestSalesUseCase <- GetLatestSalesUseCase.from(
                            input = GetLatestSalesInputMock,
                            saleRepository = SaleRepositoryMock()
                        )
                        useCaseResult <- getLatestSalesUseCase.getValidateLatestSales
                    yield assertTrue(useCaseResult == NonEmptyChunk(SaleEntityMock, SaleEntityMock))
                ),

                test(
                    GetLatestSalesUseCaseError.InputFailure(
                        GetLatestSalesInputError.AmountOfSalesConstructionFailed(
                            AmountValueError.AmountIsNegative(-1)
                        )
                    ).toString
                )(
                    for
                        getLatestSalesUseCase <- GetLatestSalesUseCase.from(
                            input = GetLatestSalesNegativeAmountOfSalesFailureInputMock,
                            saleRepository = SaleRepositoryMock()
                        )
                        useCaseResult <- getLatestSalesUseCase.getValidateLatestSales.cause
                        expected <- ZIO.fail(
                            GetLatestSalesUseCaseError.InputFailure(
                                GetLatestSalesInputError.AmountOfSalesConstructionFailed(
                                    AmountValueError.AmountIsNegative(-1)
                                )
                            )
                        ).cause
                    yield assertTrue(useCaseResult.contains(expected))
                ),

                test(
                    GetLatestSalesUseCaseError.SaleRepositoryFailure(
                        SaleRepositoryError.FindLatestSalesByAmountFailed(
                            RepositoryError.Failure(MockThrowable)
                        )
                    ).toString
                )(
                    for
                        getLatestSalesUseCase <- GetLatestSalesUseCase.from(
                            input = GetLatestSalesInputMock,
                            saleRepository = SaleRepositoryFindLatestSalesByAmountFailureMock
                        )
                        useCaseResult <- getLatestSalesUseCase.getValidateLatestSales.cause
                        expected <- ZIO.fail(
                            GetLatestSalesUseCaseError.SaleRepositoryFailure(
                                SaleRepositoryError.FindLatestSalesByAmountFailed(
                                    RepositoryError.Failure(MockThrowable)
                                )
                            )
                        ).cause
                    yield assertTrue(useCaseResult.contains(expected))
                )
            )
        )