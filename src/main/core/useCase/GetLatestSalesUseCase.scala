package core.useCase

import boundary.repository.SaleRepository
import zio.*

case class GetLatestSalesUseCase private(
    input: GetLatestSalesInput,
    saleRepository: SaleRepository
)

object GetLatestSalesUseCase:

    def from(
        input: GetLatestSalesInput,
        saleRepository: SaleRepository
    ): UIO[CreateSaleUseCase] =
        ZIO.succeed(
            GetLatestSalesUseCase(
                input,
                saleRepository
            )
        )