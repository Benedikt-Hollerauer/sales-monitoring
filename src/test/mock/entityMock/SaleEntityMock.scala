package mock.entityMock

import core.entity.SaleEntity

object SaleEntityMock extends SaleEntity(
    saleTitle = TitleValue.fromString("saleTitle")
)