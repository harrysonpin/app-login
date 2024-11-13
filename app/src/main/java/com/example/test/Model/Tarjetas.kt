package com.example.test.Model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tarjetas",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["user_id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )]
)

data class Tarjetas(
    @PrimaryKey(autoGenerate = true)
    val tarjeta_id: Int = 0,
    var tipo_tarjeta: String,
    var num_tarjeta: String,
    val user_id: Int

)