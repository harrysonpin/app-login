package com.example.test.Screen



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.test.Model.Tarjetas
import com.example.test.Repository.TarjetasRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCrearTarjeta(
    navController: NavHostController,
    tarjetasRepository: TarjetasRepository,
    userId: Int,
    tarjetaId: Int? = null
) {
    var tipoTarjeta by remember { mutableStateOf("") }
    var numTarjeta by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }
    val ambito = rememberCoroutineScope()

    LaunchedEffect(tarjetaId) {
        tarjetaId?.let {
            val tarjeta = tarjetasRepository.getTarjetaById(it)
            tarjeta?.let {
                tipoTarjeta = it.tipo_tarjeta
                numTarjeta = it.num_tarjeta
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (tarjetaId == null) "Crear Tarjeta" else "Editar Tarjeta") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = tipoTarjeta,
                onValueChange = { tipoTarjeta = it },
                label = { Text("Tipo de Tarjeta") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = numTarjeta,
                onValueChange = { numTarjeta = it },
                label = { Text("Número de Tarjeta") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    if (tipoTarjeta.isNotEmpty() && numTarjeta.isNotEmpty()) {
                        ambito.launch {
                            val tarjeta = Tarjetas(
                                tipo_tarjeta = tipoTarjeta,
                                num_tarjeta = numTarjeta,
                                user_id = userId
                            )
                            if (tarjetaId == null) {
                                tarjetasRepository.insertar(tarjeta)
                            } else {
                                tarjetasRepository.actualizar(tarjeta.copy(tarjeta_id = tarjetaId))
                            }
                            navController.navigateUp()
                        }
                    } else {
                        error = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (tarjetaId == null) "Crear Tarjeta" else "Guardar Cambios")
            }
            if (error) {
                Text(
                    text = "Por favor, complete todos los campos",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

