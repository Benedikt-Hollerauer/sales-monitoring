package core.useCase

import core.value.MoneyValue
import boundary.input.CalculateProfitInput
import error.entityError.SaleEntityError
import zio.*

case class CalculateProfitUseCase private(
    input: CalculateProfitInput
):
    def calculateProfit: IO[SaleEntityError, MoneyValue] =
        val test = input.sales
            .flatMap(saleEntity => NonEmptyChunk(saleEntity.calculateProfit))

        val test2 = test.map(x => (x.map(y => NonEmptyChunk(y))).map(moin => moin))

        val test3 = test2.map(x => x.flatMap(y => y.reduce((x1, x2) => ZIO.succeed(x1.amount + x2.amount))))



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