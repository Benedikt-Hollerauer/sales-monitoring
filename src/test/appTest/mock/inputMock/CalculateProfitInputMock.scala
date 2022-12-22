package appTest.mock.inputMock

import app.boundary.input.CalculateProfitInput
import app.core.entity.SaleEntity
import appTest.mock.entityMock.SaleEntityMock
import zio.*

class CalculateProfitInputMock extends CalculateProfitInput(
    sales = NonEmptyChunk(SaleEntityMock, SaleEntityMock)
)

object CalculateProfitInputMock:

    val toManySellingPriceDecimalPlacesInputFailureMock = new CalculateProfitInputMock().copy(sales = NonEmptyChunk(SaleEntityMock.toManyDecimalPlacesSellingPriceFailureMock, SaleEntityMock.toManyDecimalPlacesSellingPriceFailureMock))

    val toManySellingCostsDecimalPlacesInputFailureMock = new CalculateProfitInputMock().copy(sales = NonEmptyChunk(SaleEntityMock.toManyDecimalPlacesSellingCostsFailureMock, SaleEntityMock.toManyDecimalPlacesSellingCostsFailureMock))

    val toManySellingCostsAndSellingPriceDecimalPlacesInputFailureMock = new CalculateProfitInputMock().copy(sales = NonEmptyChunk(SaleEntityMock.toManyDecimalPlacesSellingPriceFailureMock, SaleEntityMock.toManyDecimalPlacesSellingCostsFailureMock))