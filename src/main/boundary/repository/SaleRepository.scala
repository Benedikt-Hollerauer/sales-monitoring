package boundary.repository

import core.entity.SaleEntity
import zio.*

trait SaleRepository:

    def saveSaleToRepository(sale: SaleEntity): IO[SaleRepositoryError, Unit]