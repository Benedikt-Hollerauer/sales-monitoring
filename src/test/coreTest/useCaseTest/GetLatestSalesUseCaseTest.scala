package coreTest.useCaseTest

import boundary.input.CreateSaleInput
import boundary.repository.SaleRepository
import core.entity.SaleEntity
import core.useCase.GetLatestSalesUseCase
import mock.repositoryMock.{SaleRepositoryFindLatestSalesByAmountFailureMock, SaleRepositoryMock}
import mock.entityMock.SaleEntityMock
import mock.inputMock.{GetLatestSalesInputMock, GetLatestSalesNegativeAmountOfSalesFailureInputMock}
import mock.MockThrowable
import error.useCaseError.GetLatestSalesUseCaseError
import error.inputError.GetLatestSalesInputError
import error.repositoryError.SaleRepositoryError
import error.valueError.AmountValueError
import error.repositoryError.RepositoryError
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