package boundary.input

import core.value.TitleValue
import core.value.PlatformValue
import core.value.DescriptionValue
import error.valueError.{DescriptionValueError, TitleValueError}
import zio.IO

import java.time.LocalDate

case class SearchForSaleInput(
    title: IO[TitleValueError, TitleValue],
    saleDate: IO[Exception, LocalDate],
    saleDescription: IO[DescriptionValueError, DescriptionValue],
    salesPlatform: PlatformValue,
)