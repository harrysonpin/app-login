package com.example.test.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.test.Model.Cuentas
import com.example.test.Repository.CuentasRepository
import kotlinx.coroutines.launch

@Composable
fun CuentasScreen(
    navController: NavHostController,
    userId: Int,
    cuentasRepository: CuentasRepository
) {
    var cuentas by remember { mutableStateOf<List<Cuentas>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(userId) {
        coroutineScope.launch {
            cuentas = cuentasRepository.getCuentasByUserId(userId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Cuentas Bancarias", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        cuentas.forEach { cuenta ->
            Text(text = "Tipo: ${cuenta.tipo}")
            Text(text = "Saldo: ${cuenta.saldo}")
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Button(onClick = { /* Edit account logic */ }) {
                    Text("Edit")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    coroutineScope.launch {
                        cuentasRepository.deleteCuenta(cuenta)
                        cuentas = cuentasRepository.getCuentasByUserId(userId)
                    }
                }) {
                    Text("Delete")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Navigate to create account screen */ }) {
            Text("Create Account")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigateUp() }) {
            Text("Back")
        }
    }
}