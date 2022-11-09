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
    def calculateProfit: IO[SaleEntityError, MoneyValue] = ???