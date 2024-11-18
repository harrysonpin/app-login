package com.example.test.Screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.test.Model.Prestamos
import com.example.test.Repository.PrestamosRepository
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrestamos(
    navController: NavHostController,
    userId: Int,
    prestamosRepository: PrestamosRepository
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

    var prestamos by remember { mutableStateOf<List<Prestamos>>(emptyList()) }
    val ambitoCorrutina = rememberCoroutineScope()

    LaunchedEffect(userId) {
        ambitoCorrutina.launch {
            prestamos = prestamosRepository.getPrestamosByUserId(userId)
        }
    }

    MaterialTheme(colorScheme = colores) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Préstamos Bancarios") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("crear_prestamo/$userId") }) {
                            Icon(Icons.Filled.Add, contentDescription = "Crear Préstamo")
                        }
                    }
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(prestamos) { prestamo ->
                    TarjetaPrestamo(
                        prestamo = prestamo,
                        onEliminar = {
                            ambitoCorrutina.launch {
                                prestamosRepository.eliminar(prestamo)
                                prestamos = prestamosRepository.getPrestamosByUserId(userId)
                            }
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { navController.navigate("crear_prestamo/$userId") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Solicitar Nuevo Préstamo")
                    }
                }
            }
        }
    }
}

@Composable
fun TarjetaPrestamo(
    prestamo: Prestamos,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Monto: ${formatearMonto(prestamo.monto)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tasa de Interés: ${formatearPorcentaje(prestamo.tasa_interes)}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Plazo: ${prestamo.plazo} meses",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onEliminar,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    Spacer(Modifier.width(4.dp))
                    Text("Eliminar")
                }
            }
        }
    }
}
fun formatearMonto(monto: Double): String {
    val formato = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
    return formato.format(monto)
}

fun formatearPorcentaje(tasa: Double): String {
    return String.format("%.2f%%", tasa)
}