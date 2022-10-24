package boundary.input

import zio.*

case class GetLatestSalesInput(
    amountOfSales: IO[AmountValueError, AmountValue]
)