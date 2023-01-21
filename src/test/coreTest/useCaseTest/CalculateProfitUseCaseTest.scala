package coreTest.useCaseTest

import core.useCase.CalculateProfitUseCase
import core.value.MoneyValue
import error.entityError.SaleEntityError
import error.valueError.MoneyValueError
import mock.inputMock.CalculateProfitInputMock
import zio.test.*
import zio.*

import scala.compiletime.ops.any.ToString

object CalculateProfitUseCaseTest extends ZIOSpecDefault:

    def spec =
        suite(
            CalculateProfitUseCase.toString
        )(
            suite(
                ".calculateProfit should return"
            )(
                test(
                    MoneyValue.toString
                )(
                    for
                        calculateProfitUseCase <- CalculateProfitUseCase.from(
                            input = new CalculateProfitInputMock
                        )
                        useCaseResult <- calculateProfitUseCase.calculateProfit
                    yield assertTrue(useCaseResult.amount == 28.00) &&
                        assertTrue(useCaseResult.isInstanceOf[MoneyValue])
                ),

                test(
                    SaleEntityError.SellingPriceConstructionFailed(
                        MoneyValueError.MoreThanTwoDecimalPlaces(124.32564643)
                    ).toString
                )(
                    for
                        calculateProfitUseCase <- CalculateProfitUseCase.from(
                            input = CalculateProfitInputMock.toManySellingPriceDecimalPlacesInputFailureMock,
                        )
                        useCaseResult <- calculateProfitUseCase.calculateProfit.cause
                        expected <- ZIO.fail(
                            SaleEntityError.SellingPriceConstructionFailed(
                                MoneyValueError.MoreThanTwoDecimalPlaces(124.32564643)
                            )
                        ).cause
                    yield assertTrue(useCaseResult.contains(expected))
                ),

                test(
                    SaleEntityError.SellingCostsConstructionFailed(
                        MoneyValueError.MoreThanTwoDecimalPlaces(1.347589795)
                    ).toString
                )(
                    for
                        calculateProfitUseCase <- CalculateProfitUseCase.from(
                            input = CalculateProfitInputMock.toManySellingCostsDecimalPlacesInputFailureMock,
                        )
                        useCaseResult <- calculateProfitUseCase.calculateProfit.cause
                        expected <- ZIO.fail(
                            SaleEntityError.SellingCostsConstructionFailed(
                                MoneyValueError.MoreThanTwoDecimalPlaces(1.347589795)
                            )
                        ).cause
                    yield assertTrue(useCaseResult.contains(expected))
                )
            )
        )