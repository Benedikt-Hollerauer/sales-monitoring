package mock.inputMock

import core.entity.SaleEntity
import boundary.input.CalculateProfitInput
import mock.entityMock.SaleEntityMock
import zio.*

object CalculateProfitInputMock extends CalculateProfitInput(
    sales = NonEmptyChunk(SaleEntityMock, SaleEntityMock)
)