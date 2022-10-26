package mock.entityMock

import core.entity.SaleEntity
import core.value.TitleValue

object SaleEntityMock extends SaleEntity(
    title = TitleValue.fromString("saleTitle")
)

object SaleEntityToShortTitleFailureMock extends SaleEntity(
    title = TitleValue.fromString("")
)