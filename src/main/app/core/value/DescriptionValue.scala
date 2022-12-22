package app.core.value

import app.error.valueError.DescriptionValueError
import zio.*

case class DescriptionValue private(
    description: String
)

object DescriptionValue:

    def fromString(mayBeDescription: String): IO[DescriptionValueError, DescriptionValue] =
        if(mayBeDescription.length <= 50) ZIO.fail(DescriptionValueError.DescriptionIsToShort(mayBeDescription))
        else ZIO.succeed(DescriptionValue(mayBeDescription))