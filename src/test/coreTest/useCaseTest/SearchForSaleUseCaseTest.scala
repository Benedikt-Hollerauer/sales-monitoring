package coreTest.useCaseTest

import core.useCase.SearchForSaleUseCase
import mock.repositoryMock.{SaleRepositoryFailureMock, SaleRepositoryMock, SaleRepositorySearchSalesByPlatformFailureMock, SaleRepositorySearchSalesByDateSpanFailureMock}
import mock.inputMock.{SearchForSaleInputMock, SearchForSaleToShortSaleDescriptionFailureInputMock}
import mock.MockThrowable
import mock.entityMock.SaleEntityMock
import error.useCaseError.SearchForSaleUseCaseError
import error.inputError.SearchForSaleInputError
import error.repositoryError.{SaleRepositoryError, RepositoryError}
import error.valueError.DescriptionValueError
import zio.test.*
import zio.*

object SearchForSaleUseCaseTest extends ZIOSpecDefault:

    def spec =
        suite("SearchForSaleUseCase test")(
            suite("SearchForSaleUseCase.searchValidateGetSales should return")(
                test("NonEmptyChunk[SaleEntity] when correct parameters are provided")(
                    for
                        searchForSaleUseCase <- SearchForSaleUseCase.from(
                            input = SearchForSaleInputMock,
                            saleRepository = SaleRepositoryMock()
                        )
                        useCaseResult <- searchForSaleUseCase.searchValidateGetSales
                    yield assertTrue(useCaseResult == NonEmptyChunk(SaleEntityMock, SaleEntityMock, SaleEntityMock, SaleEntityMock, SaleEntityMock, SaleEntityMock, SaleEntityMock, SaleEntityMock))
                ),

                test("SearchForSaleUseCaseError.InputFailed(SearchForSaleInputError.SaleDescriptionConstructionFailed(DescriptionValueError.DescriptionIsToShort)) when a to short CreateSaleInput.saleDescription is provided")(
                    for
                        searchForSaleUseCase <- SearchForSaleUseCase.from(
                            input = SearchForSaleToShortSaleDescriptionFailureInputMock,
                            saleRepository = SaleRepositoryMock()
                        )
                        useCaseResult <- searchForSaleUseCase.searchValidateGetSales.cause
                        expected <- ZIO.fail(
                            SearchForSaleUseCaseError.InputFailure(SearchForSaleInputError.SaleDescriptionConstructionFailed(DescriptionValueError.DescriptionIsToShort("invalid")))
                        ).cause
                    yield assertTrue(useCaseResult == expected)
                ),

                test("SearchForSaleUseCaseError.SaleRepositoryFailure(SaleRepositoryError.SearchSalesByPlatformFailed(RepositoryError.Failure))) when a failure occurred in the SaleRepository")(
                    for
                        searchForSaleUseCase <- SearchForSaleUseCase.from(
                            input = SearchForSaleInputMock,
                            saleRepository = SaleRepositorySearchSalesByPlatformFailureMock
                        )
                        useCaseResult <- searchForSaleUseCase.searchValidateGetSales.cause
                        expected <- ZIO.fail(
                            SearchForSaleUseCaseError.SaleRepositoryFailure(SaleRepositoryError.SearchSalesByPlatformFailed(RepositoryError.Failure(MockThrowable)))
                        ).cause
                    yield assertTrue(useCaseResult == expected)
                ),

                test("SearchForSaleUseCaseError.SaleRepositoryFailure(SaleRepositoryError.SearchSalesByDateSpanFailed(RepositoryError.NotFound))) when no sales were found in SaleRepository")(
                    for
                        searchForSaleUseCase <- SearchForSaleUseCase.from(
                            input = SearchForSaleInputMock,
                            saleRepository = SaleRepositorySearchSalesByDateSpanFailureMock
                        )
                        useCaseResult <- searchForSaleUseCase.searchValidateGetSales.cause
                        expected <- ZIO.fail(
                            SearchForSaleUseCaseError.SaleRepositoryFailure(SaleRepositoryError.SearchSalesByDateSpanFailed(RepositoryError.Failure(MockThrowable)))
                        ).cause
                    yield assertTrue(useCaseResult == expected)
                )
            )
        )
