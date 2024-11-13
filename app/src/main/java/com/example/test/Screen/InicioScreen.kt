package com.example.test.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.test.Model.User

@Composable
fun InicioScreen(
    navController: NavHostController,
    user: User
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "User Information", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Nombre: ${user.nombre}")
        Text(text = "Email: ${user.email}")
        Text(text = "Telefono: ${user.telefono}")
        Text(text = "Cc: ${user.cc}")
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("cuentas/${user.user_id}") }) {
            Text("Cuentas")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate("tarjetas/${user.user_id}") }) {
            Text("Tarjetas")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate("prestamos/${user.user_id}") }) {
            Text("Prestamos")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate("metricas/${user.user_id}") }) {
            Text("Metricas")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigateUp() }) {
            Text("Back")
        }
    }
}