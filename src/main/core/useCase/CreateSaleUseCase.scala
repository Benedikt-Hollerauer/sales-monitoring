package core.useCase

import boundary.input.CreateSaleInput
import boundary.repository.SaleRepository
import zio.*

case class CreateSaleUseCase private(
    input: CreateSaleInput,
    saleRepository: SaleRepository
)

object CreateSaleUseCase:

    def from(
        input: CreateSaleInput,
        saleRepository: SaleRepository
    ): UIO[CreateSaleUseCase] =
        ZIO.succeed(
            CreateSaleUseCase(
                input,
                saleRepository
            )
        )