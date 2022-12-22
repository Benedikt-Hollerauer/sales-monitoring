package app.error.repositoryError

enum SaleRepositoryError:

    case SaveSaleToRepositoryFailed(repositoryError: RepositoryError)
    case FindLatestSalesByAmountFailed(repositoryError: RepositoryError)
    case SearchSalesByPlatformFailed(repositoryError: RepositoryError)
    case SearchSalesByDateSpanFailed(repositoryError: RepositoryError)
    case SearchSalesByTitleFailed(repositoryError: RepositoryError)
    case SearchSalesByDescriptionFailed(repositoryError: RepositoryError)