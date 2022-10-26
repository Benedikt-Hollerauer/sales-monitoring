package coreTest.useCaseTest

import mock.MockThrowable
import zio.test.*
import zio.*

object SearchForSaleUseCaseTest extends ZIOSpecDefault:

    def spec =
        suite("SearchForSaleUseCase test")(
            test("SearchForSaleUseCase.searchValidateGetSales should return a NonEmptyChunk[SaleEntity] when correct parameters are provided")(
                for
                    searchForSaleUseCase <- SearchForSaleUseCase.from(
                        input = SearchForSaleInputMock,
                        saleRepository = SaleRepositoryMock
                    )
                    useCaseResult <- getLatestSalesUseCase.searchValidateGetSales
                yield assertTrue(useCaseResult == NonEmptyChunk(SaleEntityMock, SaleEntityMock))
            ),

            test("SearchForSaleUseCase.searchValidateGetSales should return a SearchForSaleUseCaseError.InputFailed(SearchForSaleInputError.SaleDescriptionConstructionFailed(DescriptionValueError.DescriptionIsToShort)) when a to short CreateSaleInput.saleDescription is provided")(
                for
                    searchForSaleUseCase <- SearchForSaleUseCase.from(
                        input = SearchForSaleToShortSaleDescriptionFailureInputMock,
                        saleRepository = SaleRepositoryMock
                    )
                    useCaseResult <- getLatestSalesUseCase.searchValidateGetSales.cause
                    expected <- ZIO.fail(
                        SearchForSaleUseCaseError.InputFailed(SearchForSaleInputError.SaleDescriptionConstructionFailed(DescriptionValueError.DescriptionIsToShort("invalid")))
                    ).cause
                yield assertTrue(useCaseResult == expected)
            ),

            test("SearchForSaleUseCase.searchValidateGetSales should return a SearchForSaleUseCaseError.SaleRepositoryFailure(SaleRepositoryError.SearchSalesByShippingFailed(RepositoryError.Failure))) when a failure occurred in the SaleRepository")(
                for
                    searchForSaleUseCase <- SearchForSaleUseCase.from(
                        input = SearchForSaleInputMock,
                        saleRepository = SaleRepositoryFailureMock
                    )
                    useCaseResult <- getLatestSalesUseCase.searchValidateGetSales.cause
                    expected <- ZIO.fail(
                        SearchForSaleUseCaseError.SaleRepositoryFailure(SaleRepositoryError.SearchSalesByShippingFailed(RepositoryError.Failure(MockThrowable)))
                    ).cause
                yield assertTrue(useCaseResult == expected)
            ),

            test("SearchForSaleUseCase.searchValidateGetSales should return a SearchForSaleUseCaseError.SaleRepositoryFailure(SaleRepositoryError.SearchSalesByShippingFailed(RepositoryError.NotFound))) when a nothing was found in SaleRepository")(
                for
                    searchForSaleUseCase <- SearchForSaleUseCase.from(
                        input = SearchForSaleInputMock,
                        saleRepository = SaleRepositoryFailureMock
                    )
                    useCaseResult <- getLatestSalesUseCase.searchValidateGetSales.cause
                    expected <- ZIO.fail(
                        SearchForSaleUseCaseError.SaleRepositoryFailure(SaleRepositoryError.SearchSalesByShippingFailed(RepositoryError.NotFound))
                    ).cause
                yield assertTrue(useCaseResult == expected)
            )
        )
