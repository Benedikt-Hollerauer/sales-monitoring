package coreTest.useCaseTest

import core.useCase.CreateSaleUseCase
import core.entity.SaleEntity
import error.useCaseError.CreateSaleUseCaseError
import error.inputError.CreateSaleInputError
import error.valueError.TitleValueError
import error.repositoryError.SaleRepositoryError
import mock.entityMock.{SaleEntityMock, SaleEntityTitleFailureMock}
import mock.inputMock.CreateSaleInputMock
import mock.repositoryMock.{SaleRepositoryMock, SaleRepositoryFailureMock}
import zio.*
import zio.test.*
import zio.test.Assertion.*

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

            test("CreateSale.createValidateSaveGetSale should return a ZIO with a CreateSaleUseCaseError.InputFailed(CreateSaleInputError.TitleConstructionFailed(TitleValueError.TitleIsToShort) when a to short CreateSaleInput.saleTitle is provided")(
                for
                    createSaleUseCase <- CreateSaleUseCase.from(
                        input = CreateSaleInputMock.copy(
                            saleEntity = SaleEntityTitleFailureMock
                        ),
                        saleRepository = SaleRepositoryMock
                    )
                    useCaseResult = createSaleUseCase.createValidateSaveGetSale
                yield assertTrue(useCaseResult == ZIO.fail(CreateSaleUseCaseError.InputFailure(CreateSaleInputError.TitleConstructionFailed(TitleValueError.TitleIsToShort("")))))
            ),

            test("CreateSale.createValidateSaveGetSale should return a ZIO with a CreateSaleUseCaseError.SaleRepositoryFailure(SaleRepositoryError.SaveSaleToRepositoryFailed)) when a failure occurred in the SaleRepository")(
                for
                    createSaleUseCase <- CreateSaleUseCase.from(
                        input = CreateSaleInputMock,
                        saleRepository = SaleRepositoryFailureMock
                    )
                    useCaseResult = createSaleUseCase.createValidateSaveGetSale
                yield assert(useCaseResult == ZIO.fail(CreateSaleUseCaseError.SaleRepositoryFailure(SaleRepositoryError.SaveSaleToRepositoryFailed(new Exception("error")))))
            )
        )