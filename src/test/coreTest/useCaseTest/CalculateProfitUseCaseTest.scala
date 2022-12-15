package coreTest.useCaseTest

import core.useCase.CalculateProfitUseCase
import core.value.MoneyValue
import mock.inputMock.CalculateProfitInputMock
import error.entityError.SaleEntityError
import error.valueError.MoneyValueError
import zio.test.*
import zio.*

object CalculateProfitUseCaseTest extends ZIOSpecDefault:

    def spec =
        suite(s"${CalculateProfitUseCase.getClass.getSimpleName}")(
            suite(".calculateProfit should return")(
                test(s"${MoneyValue.getClass.getSimpleName}")(
                    for
                        calculateProfitUseCase <- CalculateProfitUseCase.from(
                            input = new CalculateProfitInputMock
                        )
                        useCaseResult <- calculateProfitUseCase.calculateProfit
                    yield assertTrue(useCaseResult.amount == 28.00) &&
                        assertTrue(useCaseResult.isInstanceOf[MoneyValue])
                ),

                test(s"${SaleEntityError.SellingPriceConstructionFailed.getClass.getSimpleName}(${MoneyValueError.MoreThanTwoDecimalPlaces.getClass.getSimpleName})")(
                    for
                        calculateProfitUseCase <- CalculateProfitUseCase.from(
                            input = CalculateProfitInputMock.toManySellingPriceDecimalPlacesInputFailureMock,
                        )
                        useCaseResult <- calculateProfitUseCase.calculateProfit.cause
                        expected <- ZIO.fail(
                            SaleEntityError.SellingPriceConstructionFailed(MoneyValueError.MoreThanTwoDecimalPlaces(124.32564643))
                        ).cause
                    yield assertTrue(useCaseResult.contains(expected))
                ),

                test(s"${SaleEntityError.SellingCostsConstructionFailed.getClass.getSimpleName}(${MoneyValueError.MoreThanTwoDecimalPlaces.getClass.getSimpleName})")(
                    for
                        calculateProfitUseCase <- CalculateProfitUseCase.from(
                            input = CalculateProfitInputMock.toManySellingCostsDecimalPlacesInputFailureMock,
                        )
                        useCaseResult <- calculateProfitUseCase.calculateProfit.cause
                        expected <- ZIO.fail(
                            SaleEntityError.SellingCostsConstructionFailed(MoneyValueError.MoreThanTwoDecimalPlaces(1.347589795))
                        ).cause
                    yield assertTrue(useCaseResult.contains(expected))
                )
            )
        )