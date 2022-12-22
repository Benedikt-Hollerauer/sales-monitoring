package appTest.coreTest.useCaseTest

import app.boundary.input.CreateSaleInput
import app.boundary.repository.SaleRepository
import app.core.entity.SaleEntity
import app.core.useCase.SearchForSaleUseCase
import app.core.value.DescriptionValue
import app.error.inputError.SearchForSaleInputError
import app.error.repositoryError.{RepositoryError, SaleRepositoryError}
import app.error.useCaseError.SearchForSaleUseCaseError
import app.error.valueError.DescriptionValueError
import appTest.mock.{MockThrowable, StringMock}
import appTest.mock.entityMock.SaleEntityMock
import appTest.mock.inputMock.SearchForSaleInputMock
import appTest.mock.repositoryMock.{SaleRepositoryMock, SaleRepositorySearchSalesByDateSpanFailureMock, SaleRepositorySearchSalesByPlatformFailureMock}
import appTest.mock.inputMock.{SearchForSaleToShortDescriptionInputFailureMock}
import zio.test.*
import zio.*

object SearchForSaleUseCaseTest extends ZIOSpecDefault:

    def spec =
        suite(
            SearchForSaleUseCase.toString
        )(
            suite(
                ".searchValidateGetSales should return"
            )(
                test(
                    "NonEmptyChunk[SaleEntity]"
                )(
                    for
                        searchForSaleUseCase <- SearchForSaleUseCase.from(
                            input = SearchForSaleInputMock,
                            saleRepository = SaleRepositoryMock()
                        )
                        useCaseResult <- searchForSaleUseCase.searchValidateGetSales
                    yield assertTrue(
                        useCaseResult == NonEmptyChunk(
                            SaleEntityMock,
                            SaleEntityMock,
                            SaleEntityMock,
                            SaleEntityMock,
                            SaleEntityMock,
                            SaleEntityMock,
                            SaleEntityMock,
                            SaleEntityMock
                        )
                    )
                ),

                test(
                    SearchForSaleUseCaseError.InputFailure(
                        SearchForSaleInputError.SaleDescriptionConstructionFailed(
                            DescriptionValueError.DescriptionIsToShort(
                                StringMock.toShortString
                            )
                        )
                    ).toString
                )(
                    for
                        searchForSaleUseCase <- SearchForSaleUseCase.from(
                            input = SearchForSaleToShortDescriptionInputFailureMock,
                            saleRepository = SaleRepositoryMock()
                        )
                        useCaseResult <- searchForSaleUseCase.searchValidateGetSales.cause
                        expected <- ZIO.fail(
                            SearchForSaleUseCaseError.InputFailure(
                                SearchForSaleInputError.SaleDescriptionConstructionFailed(
                                    DescriptionValueError.DescriptionIsToShort(
                                        StringMock.toShortString
                                    )
                                )
                            )
                        ).cause
                    yield assertTrue(useCaseResult.contains(expected))
                ),

                test(
                    SearchForSaleUseCaseError.SaleRepositoryFailure(
                        SaleRepositoryError.SearchSalesByPlatformFailed(
                            RepositoryError.Failure(MockThrowable)
                        )
                    ).toString
                )(
                    for
                        searchForSaleUseCase <- SearchForSaleUseCase.from(
                            input = SearchForSaleInputMock,
                            saleRepository = SaleRepositorySearchSalesByPlatformFailureMock
                        )
                        useCaseResult <- searchForSaleUseCase.searchValidateGetSales.cause
                        expected <- ZIO.fail(
                            SearchForSaleUseCaseError.SaleRepositoryFailure(
                                SaleRepositoryError.SearchSalesByPlatformFailed(
                                    RepositoryError.Failure(MockThrowable)
                                )
                            )
                        ).cause
                    yield assertTrue(useCaseResult.contains(expected))
                ),

                test(
                    SearchForSaleUseCaseError.SaleRepositoryFailure(
                        SaleRepositoryError.SearchSalesByDateSpanFailed(
                            RepositoryError.NotFound
                        )
                    ).toString
                )(
                    for
                        searchForSaleUseCase <- SearchForSaleUseCase.from(
                            input = SearchForSaleInputMock,
                            saleRepository = SaleRepositorySearchSalesByDateSpanFailureMock
                        )
                        useCaseResult <- searchForSaleUseCase.searchValidateGetSales.cause
                        expected <- ZIO.fail(
                            SearchForSaleUseCaseError.SaleRepositoryFailure(
                                SaleRepositoryError.SearchSalesByDateSpanFailed(
                                    RepositoryError.Failure(MockThrowable)
                                )
                            )
                        ).cause
                    yield assertTrue(useCaseResult.contains(expected))
                )
            )
        )