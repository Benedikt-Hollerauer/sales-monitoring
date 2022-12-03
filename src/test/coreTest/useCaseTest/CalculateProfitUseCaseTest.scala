package coreTest.useCaseTest

import core.useCase.CalculateProfitUseCase
import core.value.MoneyValue
import mock.inputMock.CalculateProfitInputMock
import error.entityError.SaleEntityError
import error.valueError.MoneyValueError
import zio.test.*
import zio.*

object CalculateProfitUseCaseTest extends ZIOSpecDefault:

    def spec =
        suite(s"${CalculateProfitUseCase.getClass.getSimpleName}")(
            suite(".calculateProfit should return")(
                test(s"${MoneyValue.getClass.getSimpleName} when correct parameters are provided")(
                    for
                        calculateProfitUseCase <- CalculateProfitUseCase.from(
                            input = new CalculateProfitInputMock()
                        )
                        useCaseResult <- calculateProfitUseCase.calculateProfit
                    yield assertTrue(useCaseResult.amount == 28.00) &&
                        assertTrue(useCaseResult.isInstanceOf[MoneyValue])
                ),

                test(s"NonEmptyChunk(${SaleEntityError.SellingPriceConstructionFailed.getClass.getSimpleName}(${MoneyValueError.MoreThanTwoDecimalPlaces.getClass.getSimpleName})) when to many decimal places are provided for sellingPrice")(
                    for
                        calculateProfitUseCase <- CalculateProfitUseCase.from(
                            input = CalculateProfitInputMock.calculateProfitToManySellingPriceDecimalPlacesInputFailureMock,
                        )
                        useCaseResult <- calculateProfitUseCase.calculateProfit.cause
                        expected <- ZIO.fail(
                            NonEmptyChunk(
                                SaleEntityError.SellingPriceConstructionFailed(MoneyValueError.MoreThanTwoDecimalPlaces(124.32564643))
                            )
                        ).cause
                    yield assertTrue(useCaseResult == expected)
                ),

                test(s"NonEmptyChunk(${SaleEntityError.SellingCostsConstructionFailed.getClass.getSimpleName}(${MoneyValueError.MoreThanTwoDecimalPlaces.getClass.getSimpleName})) when to many decimal places are provided for sellingCosts")(
                    for
                        calculateProfitUseCase <- CalculateProfitUseCase.from(
                            input = CalculateProfitInputMock.calculateProfitToManySellingCostsDecimalPlacesInputFailureMock,
                        )
                        useCaseResult <- calculateProfitUseCase.calculateProfit.cause
                        expected <- ZIO.fail(
                            NonEmptyChunk(
                                SaleEntityError.SellingCostsConstructionFailed(MoneyValueError.MoreThanTwoDecimalPlaces(1.347589795))
                            )
                        ).cause
                    yield assertTrue(useCaseResult == expected)
                ),

                test(s"NonEmptyChunk(${SaleEntityError.SellingCostsConstructionFailed.getClass.getSimpleName}(${MoneyValueError.MoreThanTwoDecimalPlaces.getClass.getSimpleName}), ${SaleEntityError.SellingPriceConstructionFailed.getClass.getSimpleName}(${MoneyValueError.MoreThanTwoDecimalPlaces.getClass.getSimpleName}) when to many decimal places are provided for sellingPrice and in sellingCosts")(
                    for
                        calculateProfitUseCase <- CalculateProfitUseCase.from(
                            input = CalculateProfitInputMock.calculateProfitToManySellingCostsAndSellingPriceDecimalPlacesInputFailureMock,
                        )
                        useCaseResult <- calculateProfitUseCase.calculateProfit.cause
                        expected <- ZIO.fail(
                            NonEmptyChunk(
                                SaleEntityError.SellingCostsConstructionFailed(MoneyValueError.MoreThanTwoDecimalPlaces(1.347589795)),
                                SaleEntityError.SellingPriceConstructionFailed(MoneyValueError.MoreThanTwoDecimalPlaces(124.32564643))
                            )
                        ).cause
                    yield assertTrue(useCaseResult == expected)
                )
            )
        )