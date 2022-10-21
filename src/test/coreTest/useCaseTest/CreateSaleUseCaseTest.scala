package coreTest.useCaseTest

import zio.*
import zio.test.*

object CreateSaleUseCaseTest extends ZIOSpecDefault:

    def spec =
        suite("CreateSale suite")(
            test("CreateSale.createValidateSaveGetSale should return a ZIO with a SaleEntity when correct parameters are provided")(
                CreateSaleUseCase(
                    input = CreateSaleInputMock,
                    saleRepository = SaleRepositoryMock
                )
                    .createValidateSaveGetSale
                    .flatMap(saleEntity =>
                        assertTrue(saleEntity == SaleEntityMock)
                    )
            ),

            test("CreateSale.createValidateSaveGetSale should return a ZIO with a CreateSaleUseCaseError.CreateSaleInputError(TitleValueError.TitleConstructionWasUnsuccessful[TitleIsToShort]]] when a to short CreateSaleInput.saleTitle is provided")(
                CreateSaleUseCase(
                    input = CreateSaleInputMock.copy(
                        saleTitle = ""
                    ),
                    saleRepository = SaleRepositoryMock
                )
                    .createValidateSaveGetSale
                    .catchAll(failure =>
                        assertTrue(failure == CreateSaleUseCaseError.TitleConstructionWasUnsuccessful(CreateSa))
                    )
            ),

            test("CreateSale.createValidateSaveGetSale should return a ZIO with a CreateSaleUseCaseError[SaleRepositoryError[SaveSaleToRepositoryWasUnsuccessful]] when a failure occurred in the SaleRepository")(
                CreateSaleUseCase(
                    input = CreateSaleInputMock,
                    saleRepository = SaleRepositoryMock.copy(
                        override saveSaleToRepository = SaleRepositoryError.SaveSaleToRepositoryWasUnsuccessful(failure = new Exception("error"))
                    )
                )
                    .createValidateSaveGetSale
                    .catchAll(failure =>
                        assertTrue(failure == CreateSaleUseCaseError.SaleRepositoryError(SaveSaleToRepositoryWasUnsuccessful(new Exception("error"))))
                    )
            )
        )