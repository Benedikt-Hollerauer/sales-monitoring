package appTest.coreTest.valueTest

import app.core.value.AmountValue
import app.error.valueError.AmountValueError
import zio.test.*
import zio.*

object AmountValueTest extends ZIOSpecDefault:

    def spec =
        suite(
            AmountValue.toString
        )(
            suite(
                ".fromInt should return"
            )(
                test(
                    AmountValue.toString
                )(
                    for
                        mayBeAmountValue <- AmountValue.fromInt(
                            mayBeAmount = 1
                        )
                    yield assertTrue(mayBeAmountValue.isInstanceOf[AmountValue])
                ),

                test(
                    AmountValueError.AmountIsZero(0).toString
                )(
                    for
                        mayBeAmountValue <- AmountValue.fromInt(
                            mayBeAmount = 0
                        ).cause
                        expected <- ZIO.fail(
                            AmountValueError.AmountIsZero(0)
                        ).cause
                    yield assertTrue(mayBeAmountValue == expected)
                ),

                test(
                    AmountValueError.AmountIsNegative(-5).toString
                )(
                    for
                        mayBeAmountValue <- AmountValue.fromInt(
                            mayBeAmount = -5
                        ).cause
                        expected <- ZIO.fail(
                            AmountValueError.AmountIsNegative(-5)
                        ).cause
                    yield assertTrue(mayBeAmountValue == expected)
                )
            )
        )