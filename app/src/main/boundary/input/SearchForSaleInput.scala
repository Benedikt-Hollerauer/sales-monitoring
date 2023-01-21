package boundary.input

import core.value.{DescriptionValue, PlatformValue, TitleValue}
import error.valueError.{DescriptionValueError, TitleValueError}
import zio.IO

import java.time.LocalDate

case class SearchForSaleInput(
    saleTitle: IO[TitleValueError, TitleValue],
    saleDateSpan: IO[Exception, (LocalDate, LocalDate)],
    saleDescription: IO[DescriptionValueError, DescriptionValue],
    salesPlatform: PlatformValue
)