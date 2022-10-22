package error.inputError

import error.valueError.TitleValueError

enum CreateSaleInputError:

    case TitleConstructionFailed(titleValueError: TitleValueError)