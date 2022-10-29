package coreTest.valueTest

import core.value.DescriptionValue
import error.valueError.DescriptionValueError
import zio.test.*
import zio.*
import scala.util.Random

object DescriptionValueTest extends ZIOSpecDefault:

    def spec =
        suite("DescriptionValue test")(
            test("DescriptionValue.fromString should return a DescriptionValue when correct parameters are provided")(
                for
                    descriptionSting <- ZIO.succeed(Random.nextString(55))
                    mayBeDescriptionValue <- DescriptionValue.fromString(
                        mayBeDescription = descriptionSting
                    )
                yield assertTrue(mayBeDescriptionValue.isInstanceOf[DescriptionValue])
            ),

            test("DescriptionValue.fromString should return a DescriptionValueError.DescriptionIsToShort when a to short mayBeDescription is provided")(
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