package error.inputError

import error.valueError.TitleValueError

enum CreateSaleInputError:

    case SaleTitleConstructionFailed(titleValueError: TitleValueError)