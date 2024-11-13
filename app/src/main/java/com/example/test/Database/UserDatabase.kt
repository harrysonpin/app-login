package com.example.test.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.test.DAO.*
import com.example.test.Model.*

@Database(
    entities = [User::class, Cuentas::class, Prestamos::class, Tarjetas::class, Transacciones::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun cuentasDao(): CuentasDao
    abstract fun prestamosDao(): PrestamosDao
    abstract fun tarjetasDao(): TarjetasDao
    abstract fun transaccionesDao(): TransaccionesDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "userdatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}