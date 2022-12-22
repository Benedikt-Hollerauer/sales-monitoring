package app.error.valueError

enum MoneyValueError:

    case MoreThanTwoDecimalPlaces(invalidAmount: Double)
    case MayBeAmountIsNegative(invalidAmount: Double)