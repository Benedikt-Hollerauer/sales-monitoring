package core.useCase

import boundary.input.SearchForSaleInput
import boundary.repository.SaleRepository
import core.entity.SaleEntity
import error.inputError.SearchForSaleInputError
import error.repositoryError.{RepositoryError, SaleRepositoryError}
import error.useCaseError.SearchForSaleUseCaseError
import zio.*

case class SearchForSaleUseCase private(
    input: SearchForSaleInput,
    saleRepository: SaleRepository
):
    def searchValidateGetSales: IO[SearchForSaleUseCaseError, NonEmptyChunk[SaleEntity]] =
        for
            saleTitle <- input.saleTitle
                              .catchAll(titleValueError =>
                                  ZIO.fail(SearchForSaleUseCaseError.InputFailure(SearchForSaleInputError.SaleTitleConstructionFailed(titleValueError)))
                              )
            saleDateSpan <- input.saleDateSpan
                                 .catchAll(localDateException =>
                                     ZIO.fail(SearchForSaleUseCaseError.InputFailure(SearchForSaleInputError.SaleDateSpanConstructionFailed(localDateException)))
                                 )
            saleDescription <- input.saleDescription
                                    .catchAll(descriptionValueError =>
                                        ZIO.fail(SearchForSaleUseCaseError.InputFailure(SearchForSaleInputError.SaleDescriptionConstructionFailed(descriptionValueError)))
                                    )
            searchByTitle <- saleRepository.searchSalesByTitle(saleTitle)
                                           .catchAll(saleRepositoryError =>
                                               ZIO.fail(SearchForSaleUseCaseError.SaleRepositoryFailure(saleRepositoryError))
                                           )
            searchByDateSpan <- saleRepository.searchSalesByDateSpan(saleDateSpan)
                                              .catchAll(saleRepositoryError =>
                                                  ZIO.fail(SearchForSaleUseCaseError.SaleRepositoryFailure(saleRepositoryError))
                                              )
            searchByDescription <- saleRepository.searchSalesByDescription(saleDescription)
                                                 .catchAll(saleRepositoryError =>
                                                     ZIO.fail(SearchForSaleUseCaseError.SaleRepositoryFailure(saleRepositoryError))
                                                 )
            searchByPlatform <- saleRepository.searchSalesByPlatform(input.salesPlatform)
                                              .catchAll(saleRepositoryError =>
                                                  ZIO.fail(SearchForSaleUseCaseError.SaleRepositoryFailure(saleRepositoryError))
                                              )
            searchResult = NonEmptyChunk(searchByTitle, searchByDateSpan, searchByDescription, searchByPlatform).flatten
        yield searchResult

object SearchForSaleUseCase:

    def from(
        input: SearchForSaleInput,
        saleRepository: SaleRepository
    ): UIO[SearchForSaleUseCase] =
        ZIO.succeed(
            SearchForSaleUseCase(
                input,
                saleRepository
            )
        )