package com.example.test.Repository

import com.example.test.DAO.CuentasDao
import com.example.test.Model.Cuentas

class CuentasRepository(private val cuentasDao: CuentasDao) {
    suspend fun insertar(cuenta: Cuentas) {
        cuentasDao.insert(cuenta)
    }

    suspend fun getAllCuentas(): List<Cuentas> {
        return cuentasDao.getAllCuentas()
    }

    suspend fun actualizar(cuenta: Cuentas) {
        cuentasDao.updateCuenta(cuenta)
    }

    suspend fun getCuentaById(cuentaId: Int): Cuentas? {
        return cuentasDao.getCuentaById(cuentaId)
    }

    suspend fun getCuentasByUserId(userId: Int): List<Cuentas> {
        return cuentasDao.getCuentasByUserId(userId)
    }
    suspend fun deleteCuenta(cuenta: Cuentas) {
    cuentasDao.deleteCuenta(cuenta)
    }
}