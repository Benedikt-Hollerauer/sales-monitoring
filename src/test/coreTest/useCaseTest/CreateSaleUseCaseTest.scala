package coreTest.useCaseTest

import zio.*
import zio.test.{test, *}

object CreateSaleUseCaseTest extends ZIOSpecDefault:

    def spec = suite("CreateSale suite")(
        test("CreateSale.createValidateSaveGetSale should return a ZIO[CreateSaleInput, CreateSaleUseCaseError, SaleEntity] when correct parameters are provided") {
            val createSaleUseCase = CreateSaleUseCase(
                input = CreateSaleInputMock,
                saleRepository = SaleRepositoryMock
            )
            for
                computationResult <- createSaleUseCase.createValidateSaveGetSale
            yield assert(computationResult)(Assertion.equalTo(SaleEntityMock))
        }
    )