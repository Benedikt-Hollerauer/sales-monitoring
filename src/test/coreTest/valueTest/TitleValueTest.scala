package coreTest.valueTest

import core.value.TitleValue
import error.valueError.TitleValueError
import mock.StringMock
import zio.test.*
import zio.*

object TitleValueTest extends ZIOSpecDefault:

    def spec =
        suite(s"${TitleValue.getClass.getSimpleName}")(
            suite(".fromString should return")(
                test(s"${TitleValue.getClass.getSimpleName} when correct parameters are provided")(
                    for
                        mayBeTitleValue <- TitleValue.fromString(
                            mayBeTitle = "saleTitle"
                        )
                    yield assertTrue(mayBeTitleValue.isInstanceOf[TitleValue])
                ),

                test(s"${TitleValueError.TitleIsToShort.getClass.getSimpleName} when a to short title is provided")(
                    for
                        mayBeTitleValue <- TitleValue.fromString(
                            mayBeTitle = ""
                        ).cause
                        expected <- ZIO.fail(
                            TitleValueError.TitleIsToShort("")
                        ).cause
                    yield assertTrue(mayBeTitleValue == expected)
                ),

                test(s"${TitleValueError.TitleIsToLong.getClass.getSimpleName} when a to long title is provided")(
                    for
                        mayBeTitleValue <- TitleValue.fromString(
                            mayBeTitle = StringMock.toLongString
                        ).cause
                        expected <- ZIO.fail(
                            TitleValueError.TitleIsToLong(StringMock.toLongString)
                        ).cause
                    yield assertTrue(mayBeTitleValue == expected)
                )
            )
        )