package error.inputError

import error.repositoryError.SaleRepositoryError
import error.valueError.AmountValueError

enum GetLatestSalesInputError:

    case AmountOfSalesConstructionFailed(amountValueError: AmountValueError)