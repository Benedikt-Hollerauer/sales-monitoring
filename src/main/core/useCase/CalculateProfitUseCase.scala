package core.useCase

import core.value.MoneyValue
import boundary.input.CalculateProfitInput
import error.entityError.SaleEntityError
import zio.*

case class CalculateProfitUseCase private(
    input: CalculateProfitInput
):
    def calculateProfit: IO[SaleEntityError, MoneyValue] =
        input.sales
            .flatMap(saleEntity => NonEmptyChunk(saleEntity.calculateProfit))
            .flatMap(mayBeError => NonEmptyChunk(mayBeError.catchAll(error => ZIO.fail(error))))
            .map(moneyValue => moneyValue.flatMap(x => x))

        input.sales.reduce((x, y) =>
            for
                first <- x.calculateProfit.catchAll(error => ZIO.fail(error))
                second <- y.calculateProfit.catchAll(error => ZIO.fail(error))
                moinsen <- MoneyValue.fromDouble(first.amount + second.amount).catchAll(error => ZIO.fail(SaleEntityError.ProfitConstructionFailed(error)))
            yield moinsen
        )

object CalculateProfitUseCase:

    def from(input: CalculateProfitInput): UIO[CalculateProfitUseCase] =
        ZIO.succeed(CalculateProfitUseCase(input))