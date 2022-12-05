package coreTest.useCaseTest

import boundary.input.CreateSaleInput
import boundary.repository.SaleRepository
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
        suite(s"${GetLatestSalesUseCase.getClass.getSimpleName}")(
            suite(".getValidateLatestSales should return")(
                test("NonEmptyChunk[SaleEntity] when correct parameters are provided")(
                    for
                        getLatestSalesUseCase <- GetLatestSalesUseCase.from(
                            input = GetLatestSalesInputMock,
                            saleRepository = SaleRepositoryMock()
                        )
                        useCaseResult <- getLatestSalesUseCase.getValidateLatestSales
                    yield assertTrue(useCaseResult == NonEmptyChunk(SaleEntityMock, SaleEntityMock))
                ),

                test(s"NonEmptyChunk(${GetLatestSalesUseCaseError.InputFailure.getClass.getSimpleName}(${GetLatestSalesInputError.AmountOfSalesConstructionFailed.getClass.getSimpleName}(${AmountValueError.AmountIsNegative.getClass.getSimpleName}))) when a negative ${CreateSaleInput.getClass.getSimpleName}.amountOfSales is provided")(
                    for
                        getLatestSalesUseCase <- GetLatestSalesUseCase.from(
                            input = GetLatestSalesNegativeAmountOfSalesFailureInputMock,
                            saleRepository = SaleRepositoryMock()
                        )
                        useCaseResult <- getLatestSalesUseCase.getValidateLatestSales.cause
                        expected <- ZIO.fail(
                            NonEmptyChunk(
                                GetLatestSalesUseCaseError.InputFailure(GetLatestSalesInputError.AmountOfSalesConstructionFailed(AmountValueError.AmountIsNegative(-1)))
                            )
                        ).cause
                    yield assertTrue(useCaseResult == expected)
                ),

                test(s"NonEmptyChunk(${GetLatestSalesUseCaseError.SaleRepositoryFailure.getClass.getSimpleName}(${SaleRepositoryError.FindLatestSalesByAmountFailed.getClass.getSimpleName}(${RepositoryError.Failure.getClass.getSimpleName}(${MockThrowable.getClass.getSimpleName}))))) when a nothing was found in ${SaleRepository.getClass.getSimpleName}")(
                    for
                        getLatestSalesUseCase <- GetLatestSalesUseCase.from(
                            input = GetLatestSalesInputMock,
                            saleRepository = SaleRepositoryFindLatestSalesByAmountFailureMock
                        )
                        useCaseResult <- getLatestSalesUseCase.getValidateLatestSales.cause
                        expected <- ZIO.fail(
                            NonEmptyChunk(
                                GetLatestSalesUseCaseError.SaleRepositoryFailure(SaleRepositoryError.FindLatestSalesByAmountFailed(RepositoryError.Failure(MockThrowable)))
                            )
                        ).cause
                    yield assertTrue(useCaseResult == expected)
                ),

                test("NonEmptyChunk with multiple errors when multiple inputs are wrong")(
                    for
                        getLatestSalesUseCase <- GetLatestSalesUseCase.from(
                            input = GetLatestSalesNegativeAmountOfSalesFailureInputMock,
                            saleRepository = SaleRepositoryFindLatestSalesByAmountFailureMock
                        )
                        useCaseResult <- getLatestSalesUseCase.getValidateLatestSales.cause
                        expected <- ZIO.fail(
                            NonEmptyChunk(
                                GetLatestSalesUseCaseError.InputFailure(GetLatestSalesInputError.AmountOfSalesConstructionFailed(AmountValueError.AmountIsNegative(-1))),
                                GetLatestSalesUseCaseError.SaleRepositoryFailure(SaleRepositoryError.FindLatestSalesByAmountFailed(RepositoryError.Failure(MockThrowable)))
                            )
                        ).cause
                    yield assertTrue(useCaseResult == expected)
                )
            )
        )