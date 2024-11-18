package com.example.test.Screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
    userId: Int
) {
    val esTemaOscuro = isSystemInDarkTheme()
    val colores = if (esTemaOscuro) {
        darkColorScheme(
            primary = Color(0xFF90CAF9),
            secondary = Color(0xFF81D4FA),
            background = Color(0xFF121212),
            surface = Color(0xFF1E1E1E),
            error = Color(0xFFCF6679)
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF1976D2),
            secondary = Color(0xFF03A9F4),
            background = Color(0xFFFAFAFA),
            surface = Color(0xFFFFFFFF),
            error = Color(0xFFB00020)
        )
    }

    var monto by remember { mutableStateOf("") }
    var plazo by remember { mutableStateOf("") }
    var tasaInteres by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }
    val ambito = rememberCoroutineScope()

    MaterialTheme(colorScheme = colores) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Crear Préstamo") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Solicitar Nuevo Préstamo",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                OutlinedTextField(
                    value = monto,
                    onValueChange = { monto = it.filter { char -> char.isDigit() } },
                    label = { Text("Monto del Préstamo") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(8.dp),
                    leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = "Icono de monto") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = plazo,
                    onValueChange = { plazo = it },
                    label = { Text("Plazo (meses)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(8.dp),
                    leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = "Icono de plazo") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = tasaInteres,
                    onValueChange = { tasaInteres = it },
                    label = { Text("Tasa de Interés (%)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(8.dp),
                    leadingIcon = { Icon(Icons.Default.Percent, contentDescription = "Icono de tasa de interés") }
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        if (monto.isNotEmpty() && plazo.isNotEmpty() && tasaInteres.isNotEmpty()) {
                            ambito.launch {
                                val prestamo = Prestamos(
                                    monto = monto.toDoubleOrNull() ?: 0.0,
                                    plazo = plazo.toIntOrNull() ?: 0,
                                    tasa_interes = tasaInteres.toDoubleOrNull() ?: 0.0,
                                    user_id = userId,
                                )
                                prestamosRepository.insertar(prestamo)
                                navController.navigateUp()
                            }
                        } else {
                            error = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Crear préstamo")
                    Spacer(Modifier.width(8.dp))
                    Text("Solicitar Préstamo", modifier = Modifier.padding(vertical = 8.dp))
                }
                if (error) {
                    Text(
                        text = "Por favor, complete todos los campos correctamente",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}