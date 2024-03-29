package mock.entityMock

import core.entity.SaleEntity
import core.value.{DescriptionValue, MoneyValue, PlatformValue, TitleValue}
import mock.StringMock

import java.time.LocalDate
import scala.util.Random
import zio.*

class SaleEntityMock extends SaleEntity(
    title = TitleValue.fromString("saleTitle"),
    description = DescriptionValue.fromString(Random.nextString(55)),
    sellingPrice = MoneyValue.fromDouble(16.10),
    sellingCosts = MoneyValue.fromDouble(2.10),
    date = ZIO.succeed(LocalDate.now()),
    platform = PlatformValue.EbayClassifieds
)

object SaleEntityMock extends SaleEntityMock:

    val toShortTitleFailureMock = new SaleEntityMock().copy(title = TitleValue.fromString(StringMock.toShortString))

    val toManyDecimalPlacesSellingPriceFailureMock = new SaleEntityMock().copy(sellingPrice = MoneyValue.fromDouble(124.32564643))

    val toManyDecimalPlacesSellingCostsFailureMock = new SaleEntityMock().copy(sellingCosts = MoneyValue.fromDouble(1.347589795))