package error.repositoryError

enum SaleRepositoryError:

    case SaveSaleToRepositoryFailed(repositoryError: RepositoryError)
    case FindLatestSalesByAmountFailed(repositoryError: RepositoryError)