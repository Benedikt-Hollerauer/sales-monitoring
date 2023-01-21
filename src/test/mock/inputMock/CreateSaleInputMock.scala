package mock.inputMock

import boundary.input.CreateSaleInput
import mock.entityMock.SaleEntityMock

class CreateSaleInputMock extends CreateSaleInput(
    saleEntity = SaleEntityMock
)

object CreateSaleInputMock:

    val toShortTitleFailureMock: CreateSaleInput = new CreateSaleInputMock().copy(saleEntity = SaleEntityMock.toShortTitleFailureMock)