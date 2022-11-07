package core.entity

import core.value.{DescriptionValue, PlatformValue, TitleValue, MoneyValue}
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
)