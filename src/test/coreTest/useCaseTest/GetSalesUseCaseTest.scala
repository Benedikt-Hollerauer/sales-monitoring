package coreTest.useCaseTest

import zio.test.*
import zio.*

object GetSalesUseCaseTest extends ZIOSpecDefault:

    def spec =
        suite("GetSalesUseCase test")(
            test("GetSalesUseCase.getValidateSales should return a List[SaleEntity] when correct parameters are provided")(
                ???
            ),

            test("GetSalesUseCase.getValidateSales should return a GetSalesUseCaseError.InputFailed(GetSalesInputError.AmountOfSalesConstructionFailed(AmountValueError.AmountIsNegative) when a to negative CreateSaleInput.amountOfSales is provided")(
                ???
            ),

            test("GetSalesUseCase.getValidateSales should return a GetSalesUseCaseError.SaleRepositoryFailure(SaleRepositoryError.findLatestSalesByAmountFailed)) when a failure occurred in the SaleRepository")(
                ???
            ),
        )