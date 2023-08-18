package boundary.input

import core.entity.SaleEntity
import error.inputError.CreateSaleInputError
import zio.*

case class CreateSaleInput(
    saleEntity: SaleEntity
)