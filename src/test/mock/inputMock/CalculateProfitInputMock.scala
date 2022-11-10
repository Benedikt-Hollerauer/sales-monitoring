package mock.inputMock

import core.entity.SaleEntity
import boundary.input.CalculateProfitInput
import mock.entityMock.SaleEntityMock
import zio.*

class CalculateProfitInputMock() extends CalculateProfitInput(
    sales = NonEmptyChunk(SaleEntityMock, SaleEntityMock)
)

object CalculateProfitInputMock:

    val calculateProfitToManySellingPriceDecimalPlacesInputFailureMock = new CalculateProfitInputMock().copy(sales = NonEmptyChunk(SaleEntityMock.saleEntityToManyDecimalPlacesSellingPriceFailureMock, SaleEntityMock.saleEntityToManyDecimalPlacesSellingPriceFailureMock))

    val calculateProfitToManySellingCostsDecimalPlacesInputFailureMock = new CalculateProfitInputMock().copy(sales = NonEmptyChunk(SaleEntityMock.saleEntityToManyDecimalPlacesSellingCostsFailureMock, SaleEntityMock.saleEntityToManyDecimalPlacesSellingCostsFailureMock))