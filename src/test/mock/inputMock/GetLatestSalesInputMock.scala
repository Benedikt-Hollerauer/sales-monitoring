package mock.inputMock

import boundary.input.GetLatestSalesInput
import core.value.AmountValue
import zio.*

object GetLatestSalesInputMock extends GetLatestSalesInput(
    amountOfSales = AmountValue.fromInt(2)
)

val GetLatestSalesNegativeAmountOfSalesFailureInputMock: GetLatestSalesInput = GetLatestSalesInputMock.copy(amountOfSales = AmountValue.fromInt(-1))