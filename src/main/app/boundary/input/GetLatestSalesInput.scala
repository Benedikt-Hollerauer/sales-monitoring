package app.boundary.input

import app.core.value.AmountValue
import app.error.valueError.AmountValueError
import zio.*

case class GetLatestSalesInput(
    amountOfSales: IO[AmountValueError, AmountValue]
)