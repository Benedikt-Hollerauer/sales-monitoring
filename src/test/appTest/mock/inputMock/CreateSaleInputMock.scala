package appTest.mock.inputMock

import app.boundary.input.CreateSaleInput
import appTest.mock.entityMock.SaleEntityMock

class CreateSaleInputMock extends CreateSaleInput(
    saleEntity = SaleEntityMock
)

object CreateSaleInputMock:

    val toShortTitleFailureMock: CreateSaleInput = new CreateSaleInputMock().copy(saleEntity = SaleEntityMock.toShortTitleFailureMock)