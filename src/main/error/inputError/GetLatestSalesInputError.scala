package error.inputError

import error.valueError.AmountValueError
import error.repositoryError.SaleRepositoryError

enum GetLatestSalesInputError:

    case AmountOfSalesConstructionFailed(amountValueError: AmountValueError)