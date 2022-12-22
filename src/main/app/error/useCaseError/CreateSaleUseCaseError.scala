package app.error.useCaseError

import app.error.inputError.CreateSaleInputError
import app.error.repositoryError.SaleRepositoryError

enum CreateSaleUseCaseError:

    case InputFailure(createSaleInputError: CreateSaleInputError)
    case SaleRepositoryFailure(saleRepositoryError: SaleRepositoryError)