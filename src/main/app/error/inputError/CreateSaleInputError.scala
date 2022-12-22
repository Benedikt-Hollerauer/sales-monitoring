package app.error.inputError

import app.error.valueError.TitleValueError

enum CreateSaleInputError:

    case SaleTitleConstructionFailed(titleValueError: TitleValueError)