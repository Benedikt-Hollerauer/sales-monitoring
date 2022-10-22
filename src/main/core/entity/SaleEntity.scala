package core.entity

import core.value.TitleValue
import error.valueError.TitleValueError
import zio.*

case class SaleEntity(
    saleTitle: IO[TitleValueError, TitleValue]
)