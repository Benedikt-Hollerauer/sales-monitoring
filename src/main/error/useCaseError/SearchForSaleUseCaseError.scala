package error.useCaseError

import error.inputError.SearchForSaleInputError
import error.repositoryError.SaleRepositoryError

enum SearchForSaleUseCaseError:

    case InputFailure(searchForSaleInputError: SearchForSaleInputError)
    case SaleRepositoryFailure(saleRepositoryError: SaleRepositoryError)