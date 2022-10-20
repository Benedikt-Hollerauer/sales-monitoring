package coreTest.useCaseTest

import zio.*
import zio.test.*

object CreateSaleUseCaseTest extends ZIOSpecDefault:

    def spec =
        suite("CreateSale suite")(
            test("CreateSale.createValidateSaveGetSale should return a SaleEntity when correct parameters are provided")(
                CreateSaleUseCase(
                    input = CreateSaleInputMock,
                    saleRepository = SaleRepositoryMock
                )
                    .createValidateSaveGetSale
                    .flatMap(saleEntity =>
                        assertTrue(saleEntity == SaleEntityMock)
                    )
            )
        )