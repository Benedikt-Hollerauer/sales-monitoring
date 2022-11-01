package coreTest.valueTest

import zio.test.*
import zio.*

object MoneyValueTest extends ZIOSpecDefault:

    def spec =
        suite("MoneyValue test")(
            suite("MoneyValue.fromDouble should return")(
                test("MoneyValue when correct parameters are provided")(
                    for
                        mayBeTitleValue <- MoneyValue.fromDouble(
                            mayBeAmount = 5.11
                        )
                    yield assertTrue(mayBeTitleValue.isInstanceOf[MoneyValue])
                ),

                test("TitleValueError.TitleIsToShort when a to short title is provided")(
                    for
                        mayBeTitleValue <- TitleValue.fromString(
                            mayBeAmount = 15.37842364
                        ).cause
                        expected <- ZIO.fail(
                            MoneyValueError.MoreThanTwoDecimalPlaces(15.37842364)
                        ).cause
                    yield assertTrue(mayBeTitleValue == expected)
                )
            )
        )