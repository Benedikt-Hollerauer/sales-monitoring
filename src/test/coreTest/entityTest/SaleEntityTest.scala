package coreTest.entityTest

import mock.entityMock.SaleEntityMock
import zio.test.*

object SaleEntityTest extends ZIOSpecDefault:

    def spec =
        suite("SaleEntity test")(
            suite("SaleEntity.calculateProfit should return")(
                test("MoneyValue when correct parameters are provided")(
                    for
                        result <- SaleEntityMock.calculateProfit
                    yield assertTrue(result == MoneyValue(14.20))
                ),

                test("SaleEntityError.ProfitConstructionFailed(MoneyValueError.MoreThanTwoDecimalPlaces) when to many decimal places are provided")(
                    for
                        result <- SaleEntityToManyDecimalPlecesFailureMock.calculateProfit.cause
                        expected <- ZIO.fail(
                            SaleEntityError.ProfitConstructionFailed(MoneyValueError.MoreThanTwoDecimalPlaces(20.2222))
                        ).cause
                    yield assertTrue(result == expected)
                )
            )
        )