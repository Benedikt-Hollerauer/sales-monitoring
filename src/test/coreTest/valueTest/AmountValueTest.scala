package coreTest.valueTest

import zio.test.*
import zio.*

object AmountValueTest extends ZIOSpecDefault:

    def spec =
        suite("AmountValue test")(
            test("AmountValue.fromInt should return a AmountValue  when correct parameters are provided")(
                for
                    mayBeAmountValue <- AmountValue.fromInt(
                        mayBeAmount = 1
                    )
                yield assertTrue(mayBeAmountValue == AmountValue(1))
            ),

            test("AmountValue.fromInt should return a AmountValueError.AmountIsZero when a 0 is provided as mayBeAmount")(
                for
                    mayBeAmountValue <- AmountValue.fromInt(
                        mayBeAmount = 0
                    ).cause
                    expected <- ZIO.fail(
                        AmountValueError.AmountIsZero(0)
                    ).cause
                yield assertTrue(mayBeAmountValue == expected)
            ),

            test("AmountValue.fromInt should return a AmountValueError.AmountIsNegative when a negative mayBeAmount is provided")(
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