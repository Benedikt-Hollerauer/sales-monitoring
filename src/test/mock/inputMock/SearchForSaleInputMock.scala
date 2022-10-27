package mock.inputMock

import boundary.input.SearchForSaleInput
import core.value.{DescriptionValue, TitleValue, PlatformValue}
import zio.*
import scala.util.Random

import java.time.LocalDate

object SearchForSaleInputMock extends SearchForSaleInput(
    title = TitleValue.fromString("correct Title"),
    saleDate = ZIO.succeed(LocalDate.now()),
    saleDescription = DescriptionValue.fromString(Random.nextString(55)),
    salesPlatform = PlatformValue.EbayClassifieds
)