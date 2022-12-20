package coreTest.useCaseTest

import boundary.input.CreateSaleInput
import core.useCase.SearchForSaleUseCase
import core.value.DescriptionValue
import boundary.repository.SaleRepository
import core.entity.SaleEntity
import mock.repositoryMock.{SaleRepositoryMock, SaleRepositorySearchSalesByDateSpanFailureMock, SaleRepositorySearchSalesByPlatformFailureMock}
import mock.inputMock.{SearchForSaleInputMock, SearchForSaleToShortDescriptionInputFailureMock}
import mock.{MockThrowable, StringMock}
import mock.entityMock.SaleEntityMock
import error.useCaseError.SearchForSaleUseCaseError
import error.inputError.SearchForSaleInputError
import error.repositoryError.{RepositoryError, SaleRepositoryError}
import error.valueError.DescriptionValueError
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