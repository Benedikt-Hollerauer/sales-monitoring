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
        suite("CalculateProfitUseCase test")(
            suite("CalculateProfitUseCase.calculateProfit should return")(
                test("MoneyValue when correct parameters are provided")(
                    for
                        calculateProfitUseCase <- CalculateProfitUseCase.from(
                            input = CalculateProfitInputMock
                        )
                        useCaseResult <- calculateProfitUseCase.calculateProfit
                    yield assertTrue(useCaseResult.amount == 14.20 && useCaseResult.isInstanceOf[MoneyValue])
                ),

                test("CalculateProfitUseCaseError.SellingPriceConstructionFailed(MoneyValueError.MoreThanTwoDecimalPlaces) when to many decimal places are provided")(
                    for
                        createSaleUseCase <- CalculateProfitUseCase.from(
                            input = CalculateProfitToManyDecimalPlacesInputFailureMock,
                        )
                        useCaseResult <- createSaleUseCase.calculateProfit.cause
                        expected <- ZIO.fail(
                            SaleEntityError.SellingPriceConstructionFailed(MoneyValueError.MoreThanTwoDecimalPlaces(20.2222))
                        ).cause
                    yield assertTrue(useCaseResult == expected)
                )
            )
        )