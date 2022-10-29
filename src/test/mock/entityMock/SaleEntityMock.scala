package mock.entityMock

import core.entity.SaleEntity
import core.value.{DescriptionValue, TitleValue, PlatformValue}
import java.time.LocalDate
import scala.util.Random
import zio.*

object SaleEntityMock extends SaleEntity(
    title = TitleValue.fromString("saleTitle"),
    description = DescriptionValue.fromString(Random.nextString(55)),
    date = ZIO.succeed(LocalDate.now()),
    platform = PlatformValue.EbayClassifieds
)

object SaleEntityToShortTitleFailureMock extends SaleEntity(
    title = TitleValue.fromString(""),
    description = DescriptionValue.fromString(Random.nextString(55)),
    date = ZIO.succeed(LocalDate.now()),
    platform = PlatformValue.EbayClassifieds
)