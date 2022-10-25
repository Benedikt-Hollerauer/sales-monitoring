package error.repositoryError

enum SaleRepositoryError:

    case SaveSaleToRepositoryFailed(error: Throwable)
    case FindLatestSalesByAmountFailed(error: Throwable)