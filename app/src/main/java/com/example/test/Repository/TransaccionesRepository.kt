package com.example.test.Repository

import com.example.test.DAO.TransaccionesDao
import com.example.test.Model.Transacciones

class TransaccionesRepository(private val transaccionesDao: TransaccionesDao) {
    suspend fun insertar(transaccion: Transacciones) {
        transaccionesDao.insert(transaccion)
    }

    suspend fun getAllTransacciones(): List<Transacciones> {
        return transaccionesDao.getAllTransacciones()
    }

    suspend fun eliminar(transaccion: Transacciones) {
        transaccionesDao.deleteTransaccion(transaccion)
    }

    suspend fun actualizar(transaccion: Transacciones) {
        transaccionesDao.updateTransaccion(transaccion)
    }


}