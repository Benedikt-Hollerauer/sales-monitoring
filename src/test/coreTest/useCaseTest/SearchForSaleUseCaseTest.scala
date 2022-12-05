package coreTest.useCaseTest

import boundary.input.CreateSaleInput
import core.useCase.SearchForSaleUseCase
import core.value.DescriptionValue
import boundary.repository.SaleRepository
import mock.repositoryMock.{SaleRepositoryMock, SaleRepositorySearchSalesByDateSpanFailureMock, SaleRepositorySearchSalesByPlatformFailureMock}
import mock.inputMock.{SearchForSaleInputMock, SearchForSaleToShortDescriptionInputFailureMock}
import mock.MockThrowable
import mock.entityMock.SaleEntityMock
import error.useCaseError.SearchForSaleUseCaseError
import error.inputError.SearchForSaleInputError
import error.repositoryError.{RepositoryError, SaleRepositoryError}
import error.valueError.DescriptionValueError
import zio.test.*
import zio.*

object SearchForSaleUseCaseTest extends ZIOSpecDefault:

    def spec =
        suite(s"${SearchForSaleUseCase.getClass.getSimpleName}")(
            suite(".searchValidateGetSales should return")(
                test("NonEmptyChunk[SaleEntity] when correct parameters are provided")(
                    for
                        searchForSaleUseCase <- SearchForSaleUseCase.from(
                            input = SearchForSaleInputMock,
                            saleRepository = SaleRepositoryMock()
                        )
                        useCaseResult <- searchForSaleUseCase.searchValidateGetSales
                    yield assertTrue(useCaseResult == NonEmptyChunk(SaleEntityMock, SaleEntityMock, SaleEntityMock, SaleEntityMock, SaleEntityMock, SaleEntityMock, SaleEntityMock, SaleEntityMock))
                ),

                test(s"${SearchForSaleUseCaseError.InputFailure.getClass.getSimpleName}(${SearchForSaleInputError.SaleDescriptionConstructionFailed.getClass.getSimpleName}(${DescriptionValueError.DescriptionIsToShort.getClass.getSimpleName})) when a to short ${CreateSaleInput.getClass.getSimpleName}.saleDescription is provided")(
                    for
                        searchForSaleUseCase <- SearchForSaleUseCase.from(
                            input = SearchForSaleToShortDescriptionInputFailureMock,
                            saleRepository = SaleRepositoryMock()
                        )
                        useCaseResult <- searchForSaleUseCase.searchValidateGetSales.cause
                        expected <- ZIO.fail(
                            NonEmptyChunk(
                                SearchForSaleUseCaseError.InputFailure(SearchForSaleInputError.SaleDescriptionConstructionFailed(DescriptionValueError.DescriptionIsToShort("invalid")))
                            )
                        ).cause
                    yield assertTrue(useCaseResult == expected)
                ),

                test(s"${SearchForSaleUseCaseError.SaleRepositoryFailure.getClass.getSimpleName}(${SaleRepositoryError.SearchSalesByPlatformFailed.getClass.getSimpleName}(${RepositoryError.Failure.getClass.getSimpleName}))) when a failure occurred in the ${SaleRepository.getClass.getSimpleName}")(
                    for
                        searchForSaleUseCase <- SearchForSaleUseCase.from(
                            input = SearchForSaleInputMock,
                            saleRepository = SaleRepositorySearchSalesByPlatformFailureMock
                        )
                        useCaseResult <- searchForSaleUseCase.searchValidateGetSales.cause
                        expected <- ZIO.fail(
                            NonEmptyChunk(
                                SearchForSaleUseCaseError.SaleRepositoryFailure(SaleRepositoryError.SearchSalesByPlatformFailed(RepositoryError.Failure(MockThrowable)))
                            )
                        ).cause
                    yield assertTrue(useCaseResult == expected)
                ),

                test(s"${SearchForSaleUseCaseError.SaleRepositoryFailure.getClass.getSimpleName}(${SaleRepositoryError.SearchSalesByDateSpanFailed.getClass.getSimpleName}(RepositoryError.NotFound))) when no sales were found in ${SaleRepository.getClass.getSimpleName}")(
                    for
                        searchForSaleUseCase <- SearchForSaleUseCase.from(
                            input = SearchForSaleInputMock,
                            saleRepository = SaleRepositorySearchSalesByDateSpanFailureMock
                        )
                        useCaseResult <- searchForSaleUseCase.searchValidateGetSales.cause
                        expected <- ZIO.fail(
                            NonEmptyChunk(
                                SearchForSaleUseCaseError.SaleRepositoryFailure(SaleRepositoryError.SearchSalesByDateSpanFailed(RepositoryError.Failure(MockThrowable)))
                            )
                        ).cause
                    yield assertTrue(useCaseResult == expected)
                ),

                test("NonEmptyChunk with multiple errors when multiple inputs are wrong")(
                    for
                        searchForSaleUseCase <- SearchForSaleUseCase.from(
                            input = SearchForSaleToShortDescriptionInputFailureMock,
                            saleRepository = SaleRepositorySearchSalesByPlatformFailureMock
                        )
                        useCaseResult <- searchForSaleUseCase.searchValidateGetSales.cause
                        expected <- ZIO.fail(
                            NonEmptyChunk(
                                SearchForSaleUseCaseError.InputFailure(SearchForSaleInputError.SaleDescriptionConstructionFailed(DescriptionValueError.DescriptionIsToShort("invalid"))),
                                SearchForSaleUseCaseError.SaleRepositoryFailure(SaleRepositoryError.SearchSalesByPlatformFailed(RepositoryError.Failure(MockThrowable)))
                            )
                        ).cause
                    yield assertTrue(useCaseResult == expected)
                )
            )
        )