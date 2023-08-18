package core.value

import error.valueError.MoneyValueError
import zio.*

case class MoneyValue private(
    amount: Double
)

object MoneyValue:

    def fromDouble(mayBeAmount: Double): IO[MoneyValueError, MoneyValue] =
        if(mayBeAmount <= 0)
            ZIO.fail(MoneyValueError.MayBeAmountIsNegative(mayBeAmount))
        else if(BigDecimal.valueOf(mayBeAmount).scale > 2)
            ZIO.fail(MoneyValueError.MoreThanTwoDecimalPlaces(mayBeAmount))
        else ZIO.succeed(MoneyValue(mayBeAmount))