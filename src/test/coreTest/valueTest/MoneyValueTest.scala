package coreTest.valueTest

import zio.test.*
import zio.*

object MoneyValueTest extends ZIOSpecDefault:

    def spec =
        suite("MoneyValue test")(
            suite("MoneyValue.fromDouble should return")(
                test("MoneyValue when correct parameters are provided")(
                    for
                        mayBeMoneyValue <- MoneyValue.fromDouble(
                            mayBeAmount = 5.11
                        )
                        mayBeMoneyValue1DecimalPlace <- MoneyValue.fromDouble(
                            mayBeAmount = 5.11
                        )
                    yield
                        assertTrue(mayBeTitleValue.isInstanceOf[MoneyValue])
                        assertTrue(mayBeMoneyValue1DecimalPlace.isInstanceOf[MoneyValue])
                ),

                test("MoneyValueError.MoreThanTwoDecimalPlaces when more than 2 decimal places are provided")(
                    for
                        mayBeMoneyValue <- MoneyValue.fromDouble(
                            mayBeAmount = 15.37842364
                        ).cause
                        expected <- ZIO.fail(
                            MoneyValueError.MoreThanTwoDecimalPlaces(15.37842364)
                        ).cause
                    yield assertTrue(mayBeMoneyValue == expected)
                )
            )
        )