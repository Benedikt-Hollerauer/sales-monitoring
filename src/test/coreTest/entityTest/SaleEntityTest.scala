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
        suite(
            SaleEntity.toString
        )(
            suite(
                ".calculateProfit should return"
            )(
                test(
                    MoneyValue.toString
                )(
                    for
                        result <- new SaleEntityMock().calculateProfit
                    yield assertTrue(result.amount == 14.00) &&
                        assertTrue(result.isInstanceOf[MoneyValue])
                ),

                test(
                    SaleEntityError.SellingPriceConstructionFailed(
                        MoneyValueError.MoreThanTwoDecimalPlaces(124.32564643)
                    ).toString
                )(
                    for
                        result <- SaleEntityMock.toManyDecimalPlacesSellingPriceFailureMock.calculateProfit.cause
                        expected <- ZIO.fail(
                            SaleEntityError.SellingPriceConstructionFailed(
                                MoneyValueError.MoreThanTwoDecimalPlaces(124.32564643)
                            )
                        ).cause
                    yield assertTrue(result == expected)
                ),

                test(
                    SaleEntityError.SellingCostsConstructionFailed(
                        MoneyValueError.MoreThanTwoDecimalPlaces(1.347589795)
                    ).toString
                )(
                    for
                        result <- SaleEntityMock.toManyDecimalPlacesSellingCostsFailureMock.calculateProfit.cause
                        expected <- ZIO.fail(
                            SaleEntityError.SellingCostsConstructionFailed(
                                MoneyValueError.MoreThanTwoDecimalPlaces(1.347589795)
                            )
                        ).cause
                    yield assertTrue(result == expected)
                )
            )
        )