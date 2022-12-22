package appTest.coreTest.valueTest

import app.core.value.DescriptionValue
import app.error.valueError.DescriptionValueError
import appTest.mock.StringMock
import zio.test.*
import zio.*

import scala.util.Random

object DescriptionValueTest extends ZIOSpecDefault:

    def spec =
        suite(
            DescriptionValue.toString
        )(
            suite(
                ".fromString should return"
            )(
                test(DescriptionValue.toString)(
                    for
                        descriptionSting <- ZIO.succeed(Random.nextString(55))
                        mayBeDescriptionValue <- DescriptionValue.fromString(
                            mayBeDescription = descriptionSting
                        )
                    yield assertTrue(mayBeDescriptionValue.isInstanceOf[DescriptionValue])
                ),

                test(
                    DescriptionValueError.DescriptionIsToShort(
                        StringMock.toShortString
                    ).toString
                )(
                    for
                        mayBeDescriptionValue <- DescriptionValue.fromString(
                            mayBeDescription = StringMock.toShortString
                        ).cause
                        expected <- ZIO.fail(
                            DescriptionValueError.DescriptionIsToShort(
                                StringMock.toShortString
                            )
                        ).cause
                    yield assertTrue(mayBeDescriptionValue == expected)
                )
            )
        )