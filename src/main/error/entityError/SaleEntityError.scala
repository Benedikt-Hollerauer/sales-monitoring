package error.entityError

import error.valueError.MoneyValueError

enum SaleEntityError:

    case SellingPriceConstructionFailed(moneyValueError: MoneyValueError)
    case SellingCostsConstructionFailed(moneyValueError: MoneyValueError)
    case ProfitConstructionFailed(moneyValueError: MoneyValueError)