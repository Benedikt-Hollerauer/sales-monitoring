package app.boundary.input

import app.core.entity.SaleEntity
import zio.*

case class CalculateProfitInput(
    sales: NonEmptyChunk[SaleEntity]
)