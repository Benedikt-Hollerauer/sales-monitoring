package core.value

import error.valueError.AmountValueError
import zio.*

case class AmountValue private(
    amount: Int
)

object AmountValue:

    def fromInt(mayBeAmount: Int): IO[AmountValueError, AmountValue] =
        if(mayBeAmount == 0) ZIO.fail(AmountValueError.AmountIsZero(mayBeAmount))
        else if(mayBeAmount < 0) ZIO.fail(AmountValueError.AmountIsNegative(mayBeAmount))
        else ZIO.succeed(AmountValue(mayBeAmount))