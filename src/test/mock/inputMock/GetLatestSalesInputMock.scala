package mock.inputMock

import zio.*

object GetLatestSalesInputMock extends GetLatestSalesInput(
    amountOfSales = AmountValue.fromInt(2)
)