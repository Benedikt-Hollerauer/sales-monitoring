package coreTest.valueTest

import core.value.TitleValue
import zio.*
import zio.test.*

object TitleValueTest extends ZIOSpecDefault:

    def spec =
        suite("TitleValue test")(
            test("TitleValue.fromString should return a ZIO with a TitleValue  when correct parameters are provided")(
                TitleValue.fromString(
                    mayBeTitle = "saleTitle"
                )
                    .flatMap(mayBeTitle =>
                        assertTrue(mayBeTitle == TitleValue("saleTitle"))
                    )
            ),

            test("TitleValue.fromString should return a ZIO with a TitleValueError.TitleIsToShort when a to short title is provided")(
                TitleValue.fromString(
                    mayBeTitle = ""
                )
                    .flatMap(mayBeTitle =>
                        assertTrue(mayBeTitle == TitleValueError.TitleIsToShort(""))
                    )
            ),

            test("TitleValue.fromString should return a ZIO with a TitleValueError.TitleIsToLong when a to long title is provided")(
                TitleValue.fromString(
                    mayBeTitle = "askjiowehnoinvsdcohnfgioewoinsdvjfioweoifnhsdoijfiepwojfiosdnonview"
                )
                    .flatMap(mayBeTitle =>
                        assertTrue(mayBeTitle == TitleValueError.TitleIsToLong("askjiowehnoinvsdcohnfgioewoinsdvjfioweoifnhsdoijfiepwojfiosdnonview"))
                    )
            ),
        )