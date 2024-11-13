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
            LoginScreen(navController, userRepository)
        }
        composable("create_user") {
            CreateUserScreen(navController, userRepository)
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
                InicioScreen(navController, it)
            }
        }
        composable("cuentas/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
            CuentasScreen(navController, userId, cuentasRepository)
        }
        composable("tarjetas/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
            TarjetasScreen(navController, userId, tarjetasRepository)
        }
        composable("prestamos/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
            PrestamosScreen(navController, userId, prestamosRepository)
        }
    }
}