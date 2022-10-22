package coreTest.useCaseTest

import core.useCase.CreateSaleUseCase
import core.entity.SaleEntity
import mock.inputMock.CreateSaleInputMock
import mock.repositoryMock.SaleRepositoryMock
import zio.*
import zio.test.*

object CreateSaleUseCaseTest extends ZIOSpecDefault:

    def spec =
        suite("CreateSale test")(
            test("CreateSale.createValidateSaveGetSale should return a ZIO with a SaleEntity when correct parameters are provided")(
                for
                    createSaleUseCase <- CreateSaleUseCase.from(
                        input = CreateSaleInputMock,
                        saleRepository = SaleRepositoryMock
                    )
                    useCaseResult <- createSaleUseCase.createValidateSaveGetSale
                yield assertTrue(useCaseResult == SaleEntityMock)
            ),

            test("CreateSale.createValidateSaveGetSale should return a ZIO with a CreateSaleUseCaseError.InputError(CreateSaleInputError.TitleConstructionWasUnsuccessful[TitleValueError.TitleIsToShort]]] when a to short CreateSaleInput.saleTitle is provided")(
                for
                    createSaleUseCase <- CreateSaleUseCase.from(
                        input = input = CreateSaleInputMock.copy(
                            saleEntity = SaleEntityMock
                        ),
                        saleRepository = SaleRepositoryMock
                    )
                    useCaseResult <- createSaleUseCase.createValidateSaveGetSale
                yield useCaseResult.catchAll(error => assertTrue(error == CreateSaleUseCaseError.InputError(CreateSaleInputError.TitleConstructionWasUnsuccessful(TitleValueError.TitleIsToShort))))
            ),

            test("CreateSale.createValidateSaveGetSale should return a ZIO with a CreateSaleUseCaseError.SaleRepositoryError(SaleRepositoryError.SaveSaleToRepositoryWasUnsuccessful)) when a failure occurred in the SaleRepository")(
                for
                    createSaleUseCase <- CreateSaleUseCase.from(
                        input = CreateSaleInputMock,
                        saleRepository = SaleRepositoryMock.clone(
                            override def saveSaleToRepository(sale: SaleEntity): IO[SaleRepositoryError, Unit] = ZIO.fail(SaleRepositoryError.SaveSaleToRepositoryWasUnsuccessful(new Exception("error")))
                        )
                    )
                    useCaseResult <- createSaleUseCase.createValidateSaveGetSale
                yield useCaseResult.catchAll(error => assertTrue(error == CreateSaleUseCaseError.SaleRepositoryError(SaleRepositoryError.SaveSaleToRepositoryWasUnsuccessful(new Exception("error")))))
            )
        )