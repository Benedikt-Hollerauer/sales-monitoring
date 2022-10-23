package coreTest.valueTest

import core.value.TitleValue
import error.valueError.TitleValueError
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
                for
                    mayBeTitleValue <- TitleValue.fromString(
                        mayBeTitle = ""
                    ).cause
                    expected <- ZIO.fail(TitleValueError.TitleIsToShort("")).cause
                yield assertTrue(mayBeTitleValue == expected)
            ),

            test("TitleValue.fromString should return a ZIO with a TitleValueError.TitleIsToLong when a to long title is provided")(
                for
                    mayBeTitleValue <- TitleValue.fromString(
                        mayBeTitle = "GW5FTy3GtEtMrDjAZbFfVadNXUYEbbkcHEMC3fkqeXFhfmibVbvCRcMJ4f23Un7bnGqNgNZYj5r79mWAbPZBjUK7r43zNtGWmAdpVqBfzihGiBSeaHq"
                    ).cause
                    expected <- ZIO.fail(TitleValueError.TitleIsToLong("GW5FTy3GtEtMrDjAZbFfVadNXUYEbbkcHEMC3fkqeXFhfmibVbvCRcMJ4f23Un7bnGqNgNZYj5r79mWAbPZBjUK7r43zNtGWmAdpVqBfzihGiBSeaHq")).cause
                yield assertTrue(mayBeTitleValue == expected)
            ),
        )