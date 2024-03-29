package core.useCase

import boundary.input.CalculateProfitInput
import core.value.MoneyValue
import error.entityError.SaleEntityError
import zio.*

case class CalculateProfitUseCase private(
    input: CalculateProfitInput
):
    def calculateProfit: IO[SaleEntityError, MoneyValue] =
        input.sales.map(saleEntity =>
            saleEntity.calculateProfit
        ).reduce((firstMayBeProfit, secondMayBeProfit) =>
            for
                firstProfit <- firstMayBeProfit
                secondProfit <- secondMayBeProfit
                mayBeProfit <- MoneyValue.fromDouble(
                    (BigDecimal.valueOf(firstProfit.amount) + BigDecimal.valueOf(secondProfit.amount)).toDouble
                ).catchAll(error => ZIO.fail(SaleEntityError.ProfitConstructionFailed(error)))
            yield mayBeProfit
        )

object CalculateProfitUseCase:

    def from(input: CalculateProfitInput): UIO[CalculateProfitUseCase] =
        ZIO.succeed(CalculateProfitUseCase(input))