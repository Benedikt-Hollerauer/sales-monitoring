package coreTest.valueTest

import core.value.MoneyValue
import error.valueError.MoneyValueError
import zio.test.*
import zio.*

object MoneyValueTest extends ZIOSpecDefault:

    def spec =
        suite(s"${MoneyValue.getClass.getSimpleName}")(
            suite(".fromDouble should return")(
                test(s"${MoneyValue.getClass.getSimpleName} when correct parameters are provided")(
                    for
                        mayBeMoneyValue <- MoneyValue.fromDouble(
                            mayBeAmount = 5.11
                        )
                        mayBeMoneyValue1DecimalPlace <- MoneyValue.fromDouble(
                            mayBeAmount = 5.1
                        )
                    yield assertTrue(mayBeMoneyValue.isInstanceOf[MoneyValue] && mayBeMoneyValue.amount == 5.11) &&
                        assertTrue(mayBeMoneyValue1DecimalPlace.isInstanceOf[MoneyValue] && mayBeMoneyValue1DecimalPlace.amount == 5.1)
                ),

                test(s"${MoneyValueError.MayBeAmountIsNegative.getClass.getSimpleName} when a negative amount is provided")(
                    for
                        mayBeMoneyValue <- MoneyValue.fromDouble(
                            mayBeAmount = -1.1
                        ).cause
                        expected <- ZIO.fail(
                            MoneyValueError.MayBeAmountIsNegative(-1.1)
                        ).cause
                    yield assertTrue(mayBeMoneyValue == expected)
                ),

                test(s"${MoneyValueError.MoreThanTwoDecimalPlaces.getClass.getSimpleName} when more than 2 decimal places are provided")(
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