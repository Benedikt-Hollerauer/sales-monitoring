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
                CreateSaleUseCase.from(
                    input = CreateSaleInputMock,
                    saleRepository = SaleRepositoryMock
                )
                    .createValidateSaveGetSale
                    .flatMap(saleEntity =>
                        assertTrue(saleEntity == SaleEntityMock)
                    )
            ),

            test("CreateSale.createValidateSaveGetSale should return a ZIO with a CreateSaleUseCaseError.InputError(CreateSaleInputError.TitleConstructionWasUnsuccessful[TitleValueError.TitleIsToShort]]] when a to short CreateSaleInput.saleTitle is provided")(
                CreateSaleUseCase.from(
                    input = CreateSaleInputMock.copy(
                        saleEntity = SaleEntityMock
                    ),
                    saleRepository = SaleRepositoryMock
                )
                    .createValidateSaveGetSale
                    .catchAll(failure =>
                        assertTrue(failure == CreateSaleUseCaseError.InputError(CreateSaleInputError.TitleConstructionWasUnsuccessful(TitleValueError.TitleIsToShort)))
                    )
            ),

            test("CreateSale.createValidateSaveGetSale should return a ZIO with a CreateSaleUseCaseError.SaleRepositoryError(SaleRepositoryError.SaveSaleToRepositoryWasUnsuccessful)) when a failure occurred in the SaleRepository")(
                CreateSaleUseCase.from(
                    input = CreateSaleInputMock,
                    saleRepository = SaleRepositoryMock.clone(
                        override def saveSaleToRepository(sale: SaleEntity): IO[SaleRepositoryError, Unit] = ZIO.fail(SaleRepositoryError.SaveSaleToRepositoryWasUnsuccessful(new Exception("error")))
                    )
                )
                    .createValidateSaveGetSale
                    .catchAll(failure =>
                        assertTrue(failure == CreateSaleUseCaseError.SaleRepositoryError(SaleRepositoryError.SaveSaleToRepositoryWasUnsuccessful(new Exception("error"))))
                    )
            )
        )