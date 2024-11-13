package com.example.test.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users") //se va a convertir en una tabla para la BD, llamada users o miembros
data class User(
    @PrimaryKey(autoGenerate = true)
    val user_id: Int = 0,
    var nombre: String,
    var contraseña: String,
    var cc: String,
    var telefono: String,
    var email: String

)