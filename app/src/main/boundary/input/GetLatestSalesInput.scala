package boundary.input

import core.value.AmountValue
import error.valueError.AmountValueError
import zio.*

case class GetLatestSalesInput(
    amountOfSales: IO[AmountValueError, AmountValue]
)