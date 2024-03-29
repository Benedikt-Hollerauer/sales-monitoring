package core.value

import error.valueError.TitleValueError
import zio.*

case class TitleValue private(
    title: String
)

object TitleValue:

    def fromString(mayBeTitle: String): IO[TitleValueError, TitleValue] =
        if(mayBeTitle.length <= 7) ZIO.fail(TitleValueError.TitleIsToShort(mayBeTitle))
        else if(mayBeTitle.length >= 60) ZIO.fail(TitleValueError.TitleIsToLong(mayBeTitle))
        else ZIO.succeed(
            TitleValue(
                title = mayBeTitle
            )
        )