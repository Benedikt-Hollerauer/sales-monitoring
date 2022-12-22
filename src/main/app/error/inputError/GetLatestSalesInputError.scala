package app.error.inputError

import app.error.repositoryError.SaleRepositoryError
import app.error.valueError.AmountValueError

enum GetLatestSalesInputError:

    case AmountOfSalesConstructionFailed(amountValueError: AmountValueError)