package mock.inputMock

import boundary.input.SearchForSaleInput
import core.value.{DescriptionValue, PlatformValue, TitleValue}
import mock.StringMock
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