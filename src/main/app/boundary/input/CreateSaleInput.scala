package app.boundary.input

import app.core.entity.SaleEntity
import app.error.inputError.CreateSaleInputError
import zio.*

case class CreateSaleInput(
    saleEntity: SaleEntity
)