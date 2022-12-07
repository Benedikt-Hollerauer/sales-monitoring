package core.useCase

import core.value.MoneyValue
import boundary.input.CalculateProfitInput
import error.entityError.SaleEntityError
import zio.*

case class CalculateProfitUseCase private(
    input: CalculateProfitInput
):
    def calculateProfit: IO[NonEmptyChunk[SaleEntityError], MoneyValue] =
        input.sales.map(saleEntity =>
            saleEntity.calculateProfit
        ).reduce((firstMayBeProfit, secondMayBeProfit) =>
            (for
                firstProfit <- firstMayBeProfit
                secondProfit <- secondMayBeProfit
                mayBeProfit <- MoneyValue.fromDouble(
                    (BigDecimal.valueOf(firstProfit.amount) + BigDecimal.valueOf(secondProfit.amount)).toDouble
                ).catchAll(error => ZIO.fail(SaleEntityError.ProfitConstructionFailed(error)))
            yield mayBeProfit).catchAll(x => ZIO.fail(NonEmptyChunk(x)))
        )

object CalculateProfitUseCase:

    def from(input: CalculateProfitInput): UIO[CalculateProfitUseCase] =
        ZIO.succeed(CalculateProfitUseCase(input))