package com.example.test.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.test.Model.Transacciones

@Dao
interface TransaccionesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaccion: Transacciones)

    @Query("SELECT * FROM transacciones")
    suspend fun getAllTransacciones(): List<Transacciones>

    @Delete
    suspend fun deleteTransaccion(transaccion: Transacciones)

    @Update
    suspend fun updateTransaccion(transaccion: Transacciones)


}