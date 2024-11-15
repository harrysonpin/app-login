package com.example.test.Screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.test.Model.Prestamos
import com.example.test.Repository.PrestamosRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCrearPrestamo(
    navController: NavHostController,
    prestamosRepository: PrestamosRepository,
    userId: Int,
    prestamoId: Int? = null
) {
    var monto by remember { mutableStateOf("") }
    var plazo by remember { mutableStateOf("") }
    var tasaInteres by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }
    val ambito = rememberCoroutineScope()

    LaunchedEffect(prestamoId) {
        prestamoId?.let {
            val prestamo = prestamosRepository.getPrestamoById(it)
            prestamo?.let {
                monto = it.monto.toString()
                plazo = it.plazo.toString()
                tasaInteres = it.tasa_interes.toString()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (prestamoId == null) "Crear Préstamo" else "Editar Préstamo") },
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
                value = monto,
                onValueChange = { monto = it },
                label = { Text("Monto") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = plazo,
                onValueChange = { plazo = it },
                label = { Text("Plazo (meses)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = tasaInteres,
                onValueChange = { tasaInteres = it },
                label = { Text("Tasa de Interés") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    if (monto.isNotEmpty() && plazo.isNotEmpty() && tasaInteres.isNotEmpty()) {
                        ambito.launch {
                            val prestamo = Prestamos(
                                monto = monto.toDouble(),
                                plazo = plazo.toInt(),
                                tasa_interes = tasaInteres.toDouble(),
                                user_id = userId
                            )
                            if (prestamoId == null) {
                                prestamosRepository.insertar(prestamo)
                            } else {
                                prestamosRepository.actualizar(prestamo.copy(prestamo_id = prestamoId))
                            }
                            navController.navigateUp()
                        }
                    } else {
                        error = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (prestamoId == null) "Crear Préstamo" else "Guardar Cambios")
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