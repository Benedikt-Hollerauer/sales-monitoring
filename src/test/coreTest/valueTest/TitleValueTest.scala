package coreTest.valueTest

import core.value.TitleValue
import error.valueError.TitleValueError
import zio.test.*
import zio.*

object TitleValueTest extends ZIOSpecDefault:

    def spec =
        suite("TitleValue test")(
            test("TitleValue.fromString should return a TitleValue  when correct parameters are provided")(
                for
                    mayBeTitleValue <- TitleValue.fromString(
                        mayBeTitle = "saleTitle"
                    )
                yield assertTrue(mayBeTitleValue.isInstanceOf[TitleValue])
            ),

            test("TitleValue.fromString should return a TitleValueError.TitleIsToShort when a to short title is provided")(
                for
                    mayBeTitleValue <- TitleValue.fromString(
                        mayBeTitle = ""
                    ).cause
                    expected <- ZIO.fail(
                        TitleValueError.TitleIsToShort("")
                    ).cause
                yield assertTrue(mayBeTitleValue == expected)
            ),

            test("TitleValue.fromString should return a TitleValueError.TitleIsToLong when a to long title is provided")(
                for
                    mayBeTitleValue <- TitleValue.fromString(
                        mayBeTitle = "fYdKgZM85TDeyhEapTudDPWDdLXk7zGydb2V34HbfSArePYuUfqVurvFdAJanM6ey"
                    ).cause
                    expected <- ZIO.fail(
                        TitleValueError.TitleIsToLong("fYdKgZM85TDeyhEapTudDPWDdLXk7zGydb2V34HbfSArePYuUfqVurvFdAJanM6ey")
                    ).cause
                yield assertTrue(mayBeTitleValue == expected)
            )
        )