package boundary.repository

import zio.*

trait SaleRepository:

    def saveSaleToRepository(sale: SaleEntity): IO[SaleRepositoryError, Unit]