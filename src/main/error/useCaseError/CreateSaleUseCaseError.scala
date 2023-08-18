package error.useCaseError

import error.inputError.CreateSaleInputError
import error.repositoryError.SaleRepositoryError

enum CreateSaleUseCaseError:

    case InputFailure(createSaleInputError: CreateSaleInputError)
    case SaleRepositoryFailure(saleRepositoryError: SaleRepositoryError)