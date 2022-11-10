package core.entity

import core.value.{DescriptionValue, MoneyValue, PlatformValue, TitleValue}
import error.entityError.SaleEntityError
import error.valueError.{DescriptionValueError, MoneyValueError, TitleValueError}
import java.time.LocalDate
import zio.*

case class SaleEntity(
    title: IO[TitleValueError, TitleValue],
    description: IO[DescriptionValueError, DescriptionValue],
    sellingPrice: IO[MoneyValueError, MoneyValue],
    sellingCosts: IO[MoneyValueError, MoneyValue],
    date: IO[Exception, LocalDate],
    platform: PlatformValue
):
    def calculateProfit: IO[SaleEntityError, MoneyValue] =
        for
            sellingPrice <- sellingPrice.catchAll(error => ZIO.fail(SaleEntityError.SellingPriceConstructionFailed(error)))
            sellingCosts <- sellingCosts.catchAll(error => ZIO.fail(SaleEntityError.SellingCostsConstructionFailed(error)))
            profitCalculationResult = (BigDecimal.valueOf(sellingPrice.amount) - BigDecimal.valueOf(sellingCosts.amount)).toDouble
            profit <- MoneyValue.fromDouble(profitCalculationResult).catchAll(error => ZIO.fail(SaleEntityError.ProfitConstructionFailed(error)))
        yield profit