package coreTest.useCaseTest

import zio.test.*
import zio.*

object GetLatestSalesUseCaseTest extends ZIOSpecDefault:

    def spec =
        suite("GetLatestSalesUseCase test")(
            test("GetLatestSalesUseCase.getValidateLatestSales should return a List[SaleEntity] when correct parameters are provided")(
                ???
            ),

            test("GetLatestSalesUseCase.getValidateLatestSales should return a GetLatestSalesUseCaseError.InputFailed(GetSalesInputError.AmountOfSalesConstructionFailed(AmountValueError.AmountIsNegative)) when a to negative CreateSaleInput.amountOfSales is provided")(
                ???
            ),

            test("GetLatestSalesUseCase.getValidateLatestSales should return a GetLatestSalesUseCaseError.SaleRepositoryFailure(SaleRepositoryError.findLatestSalesByAmountFailed)) when a failure occurred in the SaleRepository")(
                ???
            ),
        )