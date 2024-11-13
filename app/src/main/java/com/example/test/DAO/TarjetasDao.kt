package com.example.test.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.test.Model.Tarjetas

@Dao
interface TarjetasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tarjeta: Tarjetas)

    @Query("SELECT * FROM tarjetas")
    suspend fun getAllTarjetas(): List<Tarjetas>

    @Delete
    suspend fun deleteTarjeta(tarjeta: Tarjetas)

    @Update
    suspend fun updateTarjeta(tarjeta: Tarjetas)

    @Query("SELECT * FROM tarjetas WHERE tarjeta_id = :tarjetaId")
    suspend fun getTarjetaById(tarjetaId: Int): Tarjetas?

    @Query("SELECT * FROM tarjetas WHERE user_id = :userId")
    suspend fun getTarjetasByUserId(userId: Int): List<Tarjetas>
}