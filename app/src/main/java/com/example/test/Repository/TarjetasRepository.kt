package com.example.test.Repository

import com.example.test.DAO.TarjetasDao
import com.example.test.Model.Tarjetas

class TarjetasRepository(private val tarjetasDao: TarjetasDao) {
    suspend fun insertar(tarjeta: Tarjetas) {
        tarjetasDao.insert(tarjeta)
    }

    suspend fun getAllTarjetas(): List<Tarjetas> {
        return tarjetasDao.getAllTarjetas()
    }

    suspend fun eliminar(tarjeta: Tarjetas) {
        tarjetasDao.deleteTarjeta(tarjeta)
    }

    suspend fun actualizar(tarjeta: Tarjetas) {
        tarjetasDao.updateTarjeta(tarjeta)
    }

    suspend fun getTarjetaById(tarjetaId: Int): Tarjetas? {
        return tarjetasDao.getTarjetaById(tarjetaId)
    }
    suspend fun getTarjetasByUserId(userId: Int): List<Tarjetas> {
        return tarjetasDao.getTarjetasByUserId(userId)
    }
}