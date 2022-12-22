package app.boundary.input

import app.core.value.{DescriptionValue, PlatformValue, TitleValue}
import app.error.valueError.{DescriptionValueError, TitleValueError}
import zio.IO

import java.time.LocalDate

case class SearchForSaleInput(
    saleTitle: IO[TitleValueError, TitleValue],
    saleDateSpan: IO[Exception, (LocalDate, LocalDate)],
    saleDescription: IO[DescriptionValueError, DescriptionValue],
    salesPlatform: PlatformValue
)