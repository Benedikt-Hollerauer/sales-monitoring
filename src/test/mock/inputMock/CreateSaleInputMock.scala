package mock.inputMock

import boundary.input.CreateSaleInput

object CreateSaleInputMock extends CreateSaleInput(
    saleEntity = SaleEntityMock
)