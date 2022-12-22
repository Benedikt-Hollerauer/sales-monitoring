package appTest.mock.inputMock

import app.boundary.input.SearchForSaleInput
import app.core.value.{DescriptionValue, PlatformValue, TitleValue}
import appTest.mock.StringMock
import zio.*

import scala.util.Random
import java.time.LocalDate

object SearchForSaleInputMock extends SearchForSaleInput(
    saleTitle = TitleValue.fromString("correct Title"),
    saleDateSpan = ZIO.succeed((LocalDate.now(), LocalDate.now())),
    saleDescription = DescriptionValue.fromString(Random.nextString(55)),
    salesPlatform = PlatformValue.EbayClassifieds
)

val SearchForSaleToShortDescriptionInputFailureMock: SearchForSaleInput = SearchForSaleInputMock.copy(
    saleDescription = DescriptionValue.fromString(StringMock.toShortString)
)