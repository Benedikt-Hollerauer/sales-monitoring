package app.error.entityError

import app.error.valueError.MoneyValueError

enum SaleEntityError:

    case SellingPriceConstructionFailed(moneyValueError: MoneyValueError)
    case SellingCostsConstructionFailed(moneyValueError: MoneyValueError)
    case ProfitConstructionFailed(moneyValueError: MoneyValueError)