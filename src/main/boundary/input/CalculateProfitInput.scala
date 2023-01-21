package boundary.input

import core.entity.SaleEntity
import zio.*

case class CalculateProfitInput(
    sales: NonEmptyChunk[SaleEntity]
)