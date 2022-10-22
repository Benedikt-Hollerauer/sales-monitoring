package mock.entityMock

import core.entity.SaleEntity
import core.value.TitleValue

object SaleEntityMock extends SaleEntity(
    saleTitle = TitleValue.fromString("saleTitle")
)