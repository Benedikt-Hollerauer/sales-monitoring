package mock.inputMock

import core.value.AmountValue
import boundary.input.GetLatestSalesInput
import zio.*

object GetLatestSalesInputMock extends GetLatestSalesInput(
    amountOfSales = AmountValue.fromInt(2)
)

val GetLatestSalesNegativeAmountOfSalesFailureInputMock: GetLatestSalesInput = GetLatestSalesInputMock.copy(amountOfSales = AmountValue.fromInt(-1))

/*
object GetLatestSalesNegativeAmountOfSalesFailureInputMock extends GetLatestSalesInput(
    amountOfSales = AmountValue.fromInt(-1)
)*/
