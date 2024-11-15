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
import com.example.test.Model.Cuentas
import com.example.test.Repository.CuentasRepository
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCrearcuentaBan(
    navController: NavHostController,
    cuentasRepository: CuentasRepository,
    userId: Int,
    cuentaId: Int? = null
) {
    var tipoCuenta by remember { mutableStateOf("") }
    var saldoInicial by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }
    val ambito = rememberCoroutineScope()

    LaunchedEffect(cuentaId) {
        cuentaId?.let {
            val cuenta = cuentasRepository.getCuentaById(it)
            cuenta?.let {
                tipoCuenta = it.tipo
                saldoInicial = it.saldo.toString()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (cuentaId == null) "Crear Cuenta" else "Editar Cuenta") },
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
                value = tipoCuenta,
                onValueChange = { tipoCuenta = it },
                label = { Text("Nombre de la Cuenta") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = saldoInicial,
                onValueChange = { saldoInicial = it },
                label = { Text("Saldo Inicial") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    if (tipoCuenta.isNotEmpty() && saldoInicial.isNotEmpty()) {
                        ambito.launch {
                            val cuenta = Cuentas(
                                tipo = tipoCuenta,
                                saldo = saldoInicial.toDouble(),
                                user_id = userId
                            )
                            if (cuentaId == null) {
                                cuentasRepository.insertar(cuenta)
                            } else {
                                cuentasRepository.actualizar(cuenta.copy(cuenta_id = cuentaId))
                            }
                            navController.navigateUp()
                        }
                    } else {
                        error = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (cuentaId == null) "Crear Cuenta" else "Guardar Cambios")
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

