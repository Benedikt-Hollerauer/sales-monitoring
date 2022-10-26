package core.entity

import core.value.TitleValue
import error.valueError.TitleValueError
import zio.*

case class SaleEntity(
    title: IO[TitleValueError, TitleValue]
)