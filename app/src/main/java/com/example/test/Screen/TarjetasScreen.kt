package com.example.test.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.test.Model.Tarjetas
import com.example.test.Repository.TarjetasRepository
import kotlinx.coroutines.launch

@Composable
fun TarjetasScreen(
    navController: NavHostController,
    userId: Int,
    tarjetasRepository: TarjetasRepository
) {
    var tarjetas by remember { mutableStateOf<List<Tarjetas>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(userId) {
        coroutineScope.launch {
            tarjetas = tarjetasRepository.getTarjetasByUserId(userId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Tarjetas Bancarias", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        tarjetas.forEach { tarjeta ->
            Text(text = "Número: ${tarjeta.num_tarjeta}")
            Text(text = "Tipo: ${tarjeta.tipo_tarjeta}")
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Button(onClick = { /* Edit card logic */ }) {
                    Text("Edit")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    coroutineScope.launch {
                        tarjetasRepository.eliminar(tarjeta)
                        tarjetas = tarjetasRepository.getTarjetasByUserId(userId)
                    }
                }) {
                    Text("Delete")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Navigate to create card screen */ }) {
            Text("Create Card")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigateUp() }) {
            Text("Back")
        }
    }
}