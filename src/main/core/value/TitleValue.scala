package core.value

import zio.*

case class TitleValue(
    title: String
)

object TitleValue:

    def fromString(mayBeTitle: String): IO[TitleValueError, TitleValue] =
        if(mayBeTitle.length <= 7) ZIO.fail(TitleValueError.TitleIsToShort(mayBeTitle))
        else if(mayBeTitle.length >= 100) ZIO.fail(TitleValueError.TitleIsToLong(mayBeTitle))
        else ZIO.succeed(
            TitleValue(
                title = mayBeTitle
            )
        )