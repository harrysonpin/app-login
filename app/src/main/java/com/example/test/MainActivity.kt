package com.example.test

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.test.DAO.*
import com.example.test.Database.UserDatabase
import com.example.test.Repository.*
import com.example.test.Screen.navegador

class MainActivity : ComponentActivity() {
    private lateinit var userRepository: UserRepository
    private lateinit var prestamosRepository: PrestamosRepository
    private lateinit var cuentasRepository: CuentasRepository
    private lateinit var tarjetasRepository: TarjetasRepository

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the database
        val database = UserDatabase.getDatabase(application)

        // Initialize DAOs
        val userDao: UserDao = database.userDao()
        val prestamosDao = database.prestamosDao()
        val cuentasDao = database.cuentasDao()
        val tarjetasDao = database.tarjetasDao()

        // Initialize repositories
        userRepository = UserRepository(userDao)
        prestamosRepository = PrestamosRepository(prestamosDao)
        cuentasRepository = CuentasRepository(cuentasDao)
        tarjetasRepository = TarjetasRepository(tarjetasDao)

        setContent {
            navegador(
                userRepository,
                prestamosRepository,
                cuentasRepository,
                tarjetasRepository
            )
        }
    }
}