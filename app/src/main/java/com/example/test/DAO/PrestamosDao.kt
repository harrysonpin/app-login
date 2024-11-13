package com.example.test.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.test.Model.Prestamos

@Dao
interface PrestamosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(prestamos: Prestamos)

    @Query("SELECT * FROM prestamos")
    suspend fun getAllPrestamos(): List<Prestamos>

    @Delete
    suspend fun deletePrestamo(prestamo: Prestamos)

    @Update
    suspend fun updatePrestamo(prestamo: Prestamos)

    @Query("SELECT * FROM prestamos WHERE prestamo_id = :prestamoId")
    suspend fun getPrestamoById(prestamoId: Int): Prestamos?

    @Query("SELECT * FROM prestamos WHERE user_id = :userId")
    suspend fun getPrestamosByUserId(userId: Int): List<Prestamos>
}