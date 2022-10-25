package error.useCaseError

import error.inputError.GetLatestSalesInputError
import error.repositoryError.SaleRepositoryError

enum GetLatestSalesUseCaseError:

    case InputFailure(getLatestSalesInputError: GetLatestSalesInputError)
    case SaleRepositoryFailure(saleRepositoryError: SaleRepositoryError)