package coreTest.valueTest

import core.value.DescriptionValue
import error.valueError.DescriptionValueError
import zio.test.*
import zio.*
import scala.util.Random

object DescriptionValueTest extends ZIOSpecDefault:

    def spec =
        suite(s"${DescriptionValue.getClass.getSimpleName}")(
            suite(".fromString should return")(
                test(s"${DescriptionValue.getClass.getSimpleName}")(
                    for
                        descriptionSting <- ZIO.succeed(Random.nextString(55))
                        mayBeDescriptionValue <- DescriptionValue.fromString(
                            mayBeDescription = descriptionSting
                        )
                    yield assertTrue(mayBeDescriptionValue.isInstanceOf[DescriptionValue])
                ),

                test(s"${DescriptionValueError.DescriptionIsToShort.getClass.getSimpleName}")(
                    for
                        toShortDescriptionSting <- ZIO.succeed(Random.nextString(5))
                        mayBeDescriptionValue <- DescriptionValue.fromString(
                            mayBeDescription = toShortDescriptionSting
                        ).cause
                        expected <- ZIO.fail(
                            DescriptionValueError.DescriptionIsToShort(toShortDescriptionSting)
                        ).cause
                    yield assertTrue(mayBeDescriptionValue == expected)
                )
            )
        )