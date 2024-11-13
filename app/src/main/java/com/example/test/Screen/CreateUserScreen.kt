package com.example.test.Screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavHostController
import com.example.test.Model.User
import com.example.test.Repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CreateUserScreen(
    navController: NavHostController,
    userRepository: UserRepository
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var cc by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }
    var ccError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$"
        return password.matches(passwordPattern.toRegex())
    }

    fun validateFields(): Boolean {
        nameError = name.isEmpty()
        emailError = !isValidEmail(email)
        phoneError = phone.isEmpty()
        ccError = cc.isEmpty()
        passwordError = !isValidPassword(contraseña)
        return !(nameError || emailError || phoneError || ccError || passwordError)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            isError = nameError
        )
        if (nameError) {
            Text(
                text = "Name cannot be empty",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError
        )
        if (emailError) {
            Text(
                text = "Invalid email format",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth(),
            isError = phoneError
        )
        if (phoneError) {
            Text(
                text = "Phone cannot be empty",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = cc,
            onValueChange = { cc = it },
            label = { Text("Cc") },
            modifier = Modifier.fillMaxWidth(),
            isError = ccError
        )
        if (ccError) {
            Text(
                text = "Cc cannot be empty",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = contraseña,
            onValueChange = { contraseña = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError
        )
        if (passwordError) {
            Text(
                text = "Password must contain at least 8 characters, including an uppercase letter, a lowercase letter, a number, and a special character",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
    onClick = {
        if (validateFields()) {
            scope.launch {
                try {
                    withContext(Dispatchers.IO) {
                        userRepository.insertUser(User(nombre = name, email = email, telefono = phone, cc = cc, contraseña = contraseña))
                    }
                    navController.navigate("member_list")
                } catch (e: Exception) {
                    // Log the exception
                    Log.e("CreateUserScreen", "Error inserting user", e)
                }
            }
        }
    },
    modifier = Modifier.fillMaxWidth()
) {
    Text("Create User")
}
    }
}