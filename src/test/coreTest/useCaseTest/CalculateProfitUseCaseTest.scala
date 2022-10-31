package coreTest.useCaseTest

import zio.test.*
import zio.*

object CalculateProfitUseCaseTest extends ZIOSpecDefault:

    def spec =
        suite("CalculateProfitUseCase test")(
            suite("CalculateProfitUseCase.calculateValidateProfit should return")(
                test("MoneyValue when correct parameters are provided")(
                    for
                        calculateProfitUseCase <- CalculateProfitUseCase.from(
                            input = CalculateProfitInputMock,
                            saleRepository = SaleRepositoryMock()
                        )
                        useCaseResult <- calculateProfitUseCase.calculateValidateProfit
                    yield assertTrue(useCaseResult == SaleEntityMock)
                ),

                test("CalculateProfitUseCaseError.InputFailed(CalculateProfitInputError.SaleTitleConstructionFailed(TitleValueError.TitleIsToShort) when a to short CalculateProfitInput.saleTitle is provided")(
                    for
                        createSaleUseCase <- CreateSaleUseCase.from(
                            input = CreateSaleInputToShortTitleFailureMock,
                            saleRepository = SaleRepositoryMock()
                        )
                        useCaseResult <- createSaleUseCase.createValidateSaveGetSale.cause
                        expected <- ZIO.fail(
                            CreateSaleUseCaseError.InputFailure(CreateSaleInputError.SaleTitleConstructionFailed(TitleValueError.TitleIsToShort("")))
                        ).cause
                    yield assertTrue(useCaseResult == expected)
                ),

                test("CreateSaleUseCaseError.SaleRepositoryFailure(SaleRepositoryError.SaveSaleToRepositoryFailed(RepositoryError.Failure))) when a failure occurred in the SaleRepository")(
                    for
                        createSaleUseCase <- CreateSaleUseCase.from(
                            input = CreateSaleInputMock,
                            saleRepository = SaleRepositorySaveSaleToRepositoryFailureMock
                        )
                        useCaseResult <- createSaleUseCase.createValidateSaveGetSale.cause
                        expected <- ZIO.fail(
                            CreateSaleUseCaseError.SaleRepositoryFailure(SaleRepositoryError.SaveSaleToRepositoryFailed(RepositoryError.Failure(MockThrowable)))
                        ).cause
                    yield assertTrue(useCaseResult == expected)
                )
            )
        )