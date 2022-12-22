package app.error.useCaseError

import app.error.inputError.SearchForSaleInputError
import app.error.repositoryError.SaleRepositoryError

enum SearchForSaleUseCaseError:

    case InputFailure(searchForSaleInputError: SearchForSaleInputError)
    case SaleRepositoryFailure(saleRepositoryError: SaleRepositoryError)