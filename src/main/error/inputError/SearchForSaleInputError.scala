package error.inputError

import error.valueError.{DescriptionValueError, TitleValueError}

enum SearchForSaleInputError:

    case SaleTitleConstructionFailed(titleValueError: TitleValueError)
    case SaleDescriptionConstructionFailed(descriptionValueError: DescriptionValueError)
    case SaleDateSpanConstructionFailed(localDateException: Exception)