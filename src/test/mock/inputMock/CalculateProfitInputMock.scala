package mock.inputMock

import core.entity.SaleEntity
import boundary.input.CalculateProfitInput
import mock.entityMock.SaleEntityMock
import zio.*

class CalculateProfitInputMock extends CalculateProfitInput(
    sales = NonEmptyChunk(SaleEntityMock, SaleEntityMock)
)

object CalculateProfitInputMock:

    val toManySellingPriceDecimalPlacesInputFailureMock = new CalculateProfitInputMock().copy(sales = NonEmptyChunk(SaleEntityMock.toManyDecimalPlacesSellingPriceFailureMock, SaleEntityMock.toManyDecimalPlacesSellingPriceFailureMock))

    val toManySellingCostsDecimalPlacesInputFailureMock = new CalculateProfitInputMock().copy(sales = NonEmptyChunk(SaleEntityMock.toManyDecimalPlacesSellingCostsFailureMock, SaleEntityMock.saleEntityoManyDecimalPlacesSellingCostsFailureMock))