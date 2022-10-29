package coreTest.valueTest

import core.value.AmountValue
import error.valueError.AmountValueError
import zio.test.*
import zio.*

object AmountValueTest extends ZIOSpecDefault:

    def spec =
        suite("AmountValue test")(
            suite("AmountValue.fromInt should return")(
                test("AmountValue when correct parameters are provided")(
                    for
                        mayBeAmountValue <- AmountValue.fromInt(
                            mayBeAmount = 1
                        )
                    yield assertTrue(mayBeAmountValue.isInstanceOf[AmountValue])
                ),

                test("AmountValueError.AmountIsZero when a 0 is provided as mayBeAmount")(
                    for
                        mayBeAmountValue <- AmountValue.fromInt(
                            mayBeAmount = 0
                        ).cause
                        expected <- ZIO.fail(
                            AmountValueError.AmountIsZero(0)
                        ).cause
                    yield assertTrue(mayBeAmountValue == expected)
                ),

                test("AmountValueError.AmountIsNegative when a negative mayBeAmount is provided")(
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