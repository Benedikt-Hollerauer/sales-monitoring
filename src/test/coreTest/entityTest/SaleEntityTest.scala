package coreTest.entityTest

import core.value.MoneyValue
import mock.entityMock.SaleEntityMock.*
import error.entityError.SaleEntityError
import error.valueError.MoneyValueError
import mock.entityMock.SaleEntityMock
import zio.test.*
import zio.*

object SaleEntityTest extends ZIOSpecDefault:

    def spec =
        suite("SaleEntity test")(
            suite("SaleEntity.calculateProfit should return")(
                test("MoneyValue when correct parameters are provided")(
                    for
                        result <- new SaleEntityMock().calculateProfit
                    yield assertTrue(result.amount == 14.00 && result.isInstanceOf[MoneyValue])
                ),

                test("SaleEntityError.SellingPriceConstructionFailed(MoneyValueError.MoreThanTwoDecimalPlaces) when to many decimal places are provided for sellingPrice")(
                    for
                        result <- saleEntityToManyDecimalPlacesSellingPriceFailureMock.calculateProfit.cause
                        expected <- ZIO.fail(
                            SaleEntityError.SellingPriceConstructionFailed(MoneyValueError.MoreThanTwoDecimalPlaces(124.32564643))
                        ).cause
                    yield assertTrue(result == expected)
                ),

                test("SaleEntityError.SellingCostsConstructionFailed(MoneyValueError.MoreThanTwoDecimalPlaces) when to many decimal places are provided for sellingCosts")(
                    for
                        result <- saleEntityToManyDecimalPlacesSellingCostsFailureMock.calculateProfit.cause
                        expected <- ZIO.fail(
                            SaleEntityError.SellingCostsConstructionFailed(MoneyValueError.MoreThanTwoDecimalPlaces(1.347589795))
                        ).cause
                    yield assertTrue(result == expected)
                )
            )
        )