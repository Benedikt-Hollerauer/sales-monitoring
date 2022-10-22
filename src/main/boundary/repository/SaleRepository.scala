package boundary.repository

import core.entity.SaleEntity
import error.repositoryError.SaleRepositoryError
import zio.*

trait SaleRepository:

    def saveSaleToRepository(saleEntity: SaleEntity): IO[SaleRepositoryError, Unit]