package appTest.mock.inputMock

import app.boundary.input.GetLatestSalesInput
import app.core.value.AmountValue
import zio.*

object GetLatestSalesInputMock extends GetLatestSalesInput(
    amountOfSales = AmountValue.fromInt(2)
)

val GetLatestSalesNegativeAmountOfSalesFailureInputMock: GetLatestSalesInput = GetLatestSalesInputMock.copy(amountOfSales = AmountValue.fromInt(-1))