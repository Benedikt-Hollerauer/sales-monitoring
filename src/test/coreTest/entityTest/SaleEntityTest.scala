package coreTest.entityTest

import core.value.MoneyValue
import core.entity.SaleEntity
import mock.entityMock.SaleEntityMock
import error.entityError.SaleEntityError
import error.valueError.MoneyValueError
import zio.test.*
import zio.*

object SaleEntityTest extends ZIOSpecDefault:

    def spec =
        suite(s"${SaleEntity.getClass.getSimpleName}")(
            suite(".calculateProfit should return")(
                test(s"${MoneyValue.getClass.getSimpleName} when correct parameters are provided")(
                    for
                        result <- new SaleEntityMock().calculateProfit
                    yield assertTrue(result.amount == 14.00) &&
                        assertTrue(result.isInstanceOf[MoneyValue])
                ),

                test(s"${SaleEntityError.SellingPriceConstructionFailed.getClass.getSimpleName}(${MoneyValueError.MoreThanTwoDecimalPlaces.getClass.getSimpleName}) when to many decimal places are provided for sellingPrice")(
                    for
                        result <- SaleEntityMock.toManyDecimalPlacesSellingPriceFailureMock.calculateProfit.cause
                        expected <- ZIO.fail(
                            SaleEntityError.SellingPriceConstructionFailed(MoneyValueError.MoreThanTwoDecimalPlaces(124.32564643))
                        ).cause
                    yield assertTrue(result == expected)
                ),

                test(s"${SaleEntityError.SellingCostsConstructionFailed.getClass.getSimpleName}(${MoneyValueError.MoreThanTwoDecimalPlaces.getClass.getSimpleName}) when to many decimal places are provided for sellingCosts")(
                    for
                        result <- SaleEntityMock.toManyDecimalPlacesSellingCostsFailureMock.calculateProfit.cause
                        expected <- ZIO.fail(
                            SaleEntityError.SellingCostsConstructionFailed(MoneyValueError.MoreThanTwoDecimalPlaces(1.347589795))
                        ).cause
                    yield assertTrue(result == expected)
                )
            )
        )