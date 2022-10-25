package coreTest.useCaseTest

import core.useCase.GetLatestSalesUseCase
import mock.repositoryMock.SaleRepositoryMock
import mock.entityMock.SaleEntityMock
import mock.MockThrowable
import zio.test.*
import zio.*

object GetLatestSalesUseCaseTest extends ZIOSpecDefault:

    def spec =
        suite("GetLatestSalesUseCase test")(
            test("GetLatestSalesUseCase.getValidateLatestSales should return a List[SaleEntity] when correct parameters are provided")(
                for
                    getLatestSalesUseCase <- GetLatestSalesUseCase.from(
                        input = GetLatestSalesInputMock,
                        saleRepository = SaleRepositoryMock
                    )
                    useCaseResult <- getLatestSalesUseCase.getValidateLatestSales
                yield assertTrue(useCaseResult == List(SaleEntityMock, SaleEntityMock))
            ),

            test("GetLatestSalesUseCase.getValidateLatestSales should return a GetLatestSalesUseCaseError.InputFailed(GetLatestSalesInputError.AmountOfSalesConstructionFailed(AmountValueError.AmountIsZeroOrLess)) when a to negative CreateSaleInput.amountOfSales is provided")(
                for
                    getLatestSalesUseCase <- GetLatestSalesUseCase.from(
                        input = GetLatestSalesInputNegativeAmountOfSalesFailureMock,
                        saleRepository = SaleRepositoryMock
                    )
                    useCaseResult <- getLatestSalesUseCase.getValidateLatestSales.cause
                    expected <- ZIO.fail(
                        GetLatestSalesUseCaseError.InputFailure(GetLatestSalesInputError.AmountOfSalesConstructionFailed(AmountValueError.AmountIsNegative(-1)))
                    ).cause
                yield assertTrue(useCaseResult == expected)
            ),

            test("GetLatestSalesUseCase.getValidateLatestSales should return a GetLatestSalesUseCaseError.SaleRepositoryFailure(SaleRepositoryError.findLatestSalesByAmountFailed)) when a failure occurred in the SaleRepository")(
                for
                    getLatestSalesUseCase <- GetLatestSalesUseCase.from(
                        input = GetLatestSalesInputMock,
                        saleRepository = SaleRepositoryFailureMock
                    )
                    useCaseResult <- getLatestSalesUseCase.getValidateLatestSales.cause
                    expected <- ZIO.fail(
                        GetLatestSalesUseCaseError.SaleRepositoryFailure(SaleRepositoryError.findLatestSalesByAmountFailed(MockThrowable))
                    ).cause
                yield assertTrue(useCaseResult == expected)
            ),
        )