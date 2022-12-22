package appTest.coreTest.valueTest

import app.core.value.TitleValue
import app.error.valueError.TitleValueError
import appTest.mock.StringMock
import zio.test.*
import zio.*

object TitleValueTest extends ZIOSpecDefault:

    def spec =
        suite(
            TitleValue.toString
        )(
            suite(
                ".fromString should return"
            )(
                test(
                    TitleValue.toString
                )(
                    for
                        mayBeTitleValue <- TitleValue.fromString(
                            mayBeTitle = "saleTitle"
                        )
                    yield assertTrue(mayBeTitleValue.isInstanceOf[TitleValue])
                ),

                test(
                    TitleValueError.TitleIsToShort(
                        StringMock.toShortString
                    ).toString
                )(
                    for
                        mayBeTitleValue <- TitleValue.fromString(
                            mayBeTitle = StringMock.toShortString
                        ).cause
                        expected <- ZIO.fail(
                            TitleValueError.TitleIsToShort(StringMock.toShortString)
                        ).cause
                    yield assertTrue(mayBeTitleValue == expected)
                ),

                test(
                    TitleValueError.TitleIsToLong(
                        StringMock.toLongString
                    ).toString
                )(
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