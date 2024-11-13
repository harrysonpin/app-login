package com.example.test.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.test.Model.Prestamos
import com.example.test.Repository.PrestamosRepository
import kotlinx.coroutines.launch

@Composable
fun PrestamosScreen(
    navController: NavHostController,
    userId: Int,
    prestamosRepository: PrestamosRepository
) {
    var prestamos by remember { mutableStateOf<List<Prestamos>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(userId) {
        coroutineScope.launch {
            prestamos = prestamosRepository.getPrestamosByUserId(userId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Prestamos Bancarios", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        prestamos.forEach { prestamo ->
            Text(text = "Monto: ${prestamo.monto}")
            Text(text = "Interes: ${prestamo.tasa_interes}")
            Text(text = "Estado: ${prestamo.estado}")
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Button(onClick = { /* Edit loan logic */ }) {
                    Text("Edit")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    coroutineScope.launch {
                        prestamosRepository.eliminar(prestamo)
                        prestamos = prestamosRepository.getPrestamosByUserId(userId)
                    }
                }) {
                    Text("Delete")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Navigate to create loan screen */ }) {
            Text("Create Loan")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigateUp() }) {
            Text("Back")
        }
    }
}