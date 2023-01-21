package error.valueError

enum AmountValueError:

    case AmountIsZero(amount: Int)
    case AmountIsNegative(amount: Int)