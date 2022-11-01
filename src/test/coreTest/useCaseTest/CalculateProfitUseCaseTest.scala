package coreTest.useCaseTest

import zio.test.*
import zio.*

object CalculateProfitUseCaseTest extends ZIOSpecDefault:

    def spec =
        suite("CalculateProfitUseCase test")(
            suite("CalculateProfitUseCase.calculateValidateProfit should return")(
                test("MoneyValue when correct parameters are provided")(
                    for
                        calculateProfitUseCase <- CalculateProfitUseCase.from(
                            input = CalculateProfitInputMock
                        )
                        useCaseResult <- calculateProfitUseCase.calculateValidateProfit
                    yield assertTrue(useCaseResult == MoneyValue(14.20))
                )
            )
        )