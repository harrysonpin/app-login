package com.example.test.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.test.Model.Cuentas

@Dao
interface CuentasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cuenta: Cuentas)

    @Query("SELECT * FROM cuentas")
    suspend fun getAllCuentas(): List<Cuentas>

    @Delete
    suspend fun deleteCuenta(cuenta: Cuentas)

    @Update
    suspend fun updateCuenta(cuenta: Cuentas)

    @Query("SELECT * FROM cuentas WHERE cuenta_id = :cuentaId")
    suspend fun getCuentaById(cuentaId: Int): Cuentas?

    @Query("SELECT * FROM cuentas WHERE user_id = :userId")
    suspend fun getCuentasByUserId(userId: Int): List<Cuentas>
}