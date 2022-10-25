package core.useCase

import boundary.repository.SaleRepository
import boundary.input.GetLatestSalesInput
import zio.*

case class GetLatestSalesUseCase private(
    input: GetLatestSalesInput,
    saleRepository: SaleRepository
)

object GetLatestSalesUseCase:

    def from(
        input: GetLatestSalesInput,
        saleRepository: SaleRepository
    ): UIO[GetLatestSalesUseCase] =
        ZIO.succeed(
            GetLatestSalesUseCase(
                input,
                saleRepository
            )
        )