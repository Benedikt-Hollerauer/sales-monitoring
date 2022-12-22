package app.error.useCaseError

import app.error.inputError.GetLatestSalesInputError
import app.error.repositoryError.SaleRepositoryError

enum GetLatestSalesUseCaseError:

    case InputFailure(getLatestSalesInputError: GetLatestSalesInputError)
    case SaleRepositoryFailure(saleRepositoryError: SaleRepositoryError)