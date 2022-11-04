package coreTest.useCaseTest

import core.useCase.CalculateProfitUseCase
import mock.inputMock.CalculateProfitInputMock
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
                    yield assertTrue(useCaseResult == MoneyValue(14.20))
                ),

                test("CalculateProfitUseCaseError.ProfitConstructionFailed(MoneyValueError.MoreThanTwoDecimalPlaces) when to many decimal places are provided")(
                    for
                        createSaleUseCase <- CalculateProfitUseCase.from(
                            input = CalculateProfitToManyDecimalPlacesInputFailureMock,
                        )
                        useCaseResult <- createSaleUseCase.calculateProfit.cause
                        expected <- ZIO.fail(
                            CalculateProfitUseCaseError.ProfitConstructionFailed(MoneyValueError.MoreThanTwoDecimalPlaces(20.2222))
                        ).cause
                    yield assertTrue(useCaseResult == expected)
                )
            )
        )