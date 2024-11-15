package com.example.test.Screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.test.Model.User
import com.example.test.Repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCrearUsuario(
    navController: NavHostController,
    userRepository: UserRepository
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

    MaterialTheme(colorScheme = colores) {
        var nombre by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var telefono by remember { mutableStateOf("") }
        var cc by remember { mutableStateOf("") }
        var contraseña by remember { mutableStateOf("") }
        var errorNombre by remember { mutableStateOf(false) }
        var errorEmail by remember { mutableStateOf(false) }
        var errorTelefono by remember { mutableStateOf(false) }
        var errorCc by remember { mutableStateOf(false) }
        var errorContraseña by remember { mutableStateOf(false) }
        var contraseñaVisible by remember { mutableStateOf(false) }
        val ambito = rememberCoroutineScope()

        fun esEmailValido(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun esContraseñaValida(contraseña: String): Boolean {
            val patronContraseña = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$"
            return contraseña.matches(patronContraseña.toRegex())
        }

        fun validarCampos(): Boolean {
            errorNombre = nombre.isEmpty()
            errorEmail = !esEmailValido(email)
            errorTelefono = telefono.isEmpty()
            errorCc = cc.isEmpty()
            errorContraseña = !esContraseñaValida(contraseña)
            return !(errorNombre || errorEmail || errorTelefono || errorCc || errorContraseña)
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Crear Nueva Cuenta") },
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
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = errorNombre,
                    shape = RoundedCornerShape(8.dp),
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Icono de nombre") }
                )
                if (errorNombre) {
                    Text(
                        text = "El nombre no puede estar vacío",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = errorEmail,
                    shape = RoundedCornerShape(8.dp),
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Icono de correo") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                if (errorEmail) {
                    Text(
                        text = "Formato de correo electrónico inválido",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                OutlinedTextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text("Teléfono") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = errorTelefono,
                    shape = RoundedCornerShape(8.dp),
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = "Icono de teléfono") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
                if (errorTelefono) {
                    Text(
                        text = "El teléfono no puede estar vacío",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                OutlinedTextField(
                    value = cc,
                    onValueChange = { cc = it },
                    label = { Text("Cédula de Ciudadanía") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = errorCc,
                    shape = RoundedCornerShape(8.dp),
                    leadingIcon = { Icon(Icons.Default.Badge, contentDescription = "Icono de cédula") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                if (errorCc) {
                    Text(
                        text = "La cédula no puede estar vacía",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                OutlinedTextField(
                    value = contraseña,
                    onValueChange = { contraseña = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (contraseñaVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    isError = errorContraseña,
                    shape = RoundedCornerShape(8.dp),
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Icono de contraseña") },
                    trailingIcon = {
                        IconButton(onClick = { contraseñaVisible = !contraseñaVisible }) {
                            Icon(
                                if (contraseñaVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Alternar visibilidad de contraseña"
                            )
                        }
                    }
                )
                if (errorContraseña) {
                    Text(
                        text = "La contraseña debe contener al menos 8 caracteres, incluyendo mayúsculas, minúsculas, números y caracteres especiales",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Button(
                    onClick = {
                        if (validarCampos()) {
                            ambito.launch {
                                withContext(Dispatchers.IO) {
                                    userRepository.insertUser(User(nombre = nombre, email = email, telefono = telefono, cc = cc, contraseña = contraseña))
                                }
                                navController.navigate("member_list")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Crear Usuario", modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}