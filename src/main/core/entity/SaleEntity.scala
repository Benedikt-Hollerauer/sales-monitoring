package core.entity

import core.value.{DescriptionValue, PlatformValue, TitleValue}
import error.valueError.{DescriptionValueError, TitleValueError}
import java.time.LocalDate
import zio.*

case class SaleEntity(
    title: IO[TitleValueError, TitleValue],
    description: IO[DescriptionValueError, DescriptionValue],
    date: IO[Exception, LocalDate],
    platform: PlatformValue
)