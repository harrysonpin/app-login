package com.example.test.Screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.test.Repository.*
import androidx.compose.runtime.*

import com.example.test.Model.User
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun navegador(
    userRepository: UserRepository,
    prestamosRepository: PrestamosRepository,
    cuentasRepository: CuentasRepository,
    tarjetasRepository: TarjetasRepository,
) {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()

    NavHost(navController = navController, startDestination = "member_list") {
        composable("member_list") {
            PantallaInicioSesion(navController, userRepository)
        }
        composable("crear_usuario") {
            PantallaCrearUsuario(navController, userRepository)
        }
        composable("inicio/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
            var user by remember { mutableStateOf<User?>(null) }

            LaunchedEffect(userId) {
                coroutineScope.launch {
                    user = userRepository.getUserById(userId)
                }
            }

            user?.let {
                PantallaInicio(navController, it)
            }
        }
        composable("cuentas/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
            PantallaCuentas(navController, userId, cuentasRepository)
        }
        composable("tarjetas/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
            PantallaTarjetas(navController, userId, tarjetasRepository)
        }
        composable("prestamos/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
            PantallaPrestamos(navController, userId, prestamosRepository)
        }
        composable("crear_cuenta/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
            PantallaCrearCuentaBancaria(navController, cuentasRepository, userId)
        }
        composable("crear_tarjeta/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
            PantallaCrearTarjeta(navController, tarjetasRepository, userId)
        }
        composable("crear_prestamo/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
            PantallaCrearPrestamo(navController, prestamosRepository, userId)
        }
    }
}