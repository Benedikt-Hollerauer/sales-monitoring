package app.error.valueError

enum TitleValueError:

    case TitleIsToShort(notValidTitle: String)
    case TitleIsToLong(notValidTitle: String)