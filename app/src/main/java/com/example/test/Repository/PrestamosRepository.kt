package com.example.test.Repository

import com.example.test.DAO.PrestamosDao
import com.example.test.Model.Prestamos

class PrestamosRepository(private val prestamosDao: PrestamosDao) {
    suspend fun insertar(prestamo:Prestamos) {
        prestamosDao.insert(prestamo)
    }

    suspend fun getAllPrestamos(): List<Prestamos> {
        return prestamosDao.getAllPrestamos()
    }

    suspend fun eliminar(prestamo: Prestamos) {
        prestamosDao.deletePrestamo(prestamo)
    }

    suspend fun actualizar(prestamo: Prestamos) {
        prestamosDao.updatePrestamo(prestamo)
    }

    suspend fun getPrestamoById(prestamoId: Int): Prestamos? {
        return prestamosDao.getPrestamoById(prestamoId)
    }
    suspend fun getPrestamosByUserId(userId: Int): List<Prestamos> {
        return prestamosDao.getPrestamosByUserId(userId)
    }
}
