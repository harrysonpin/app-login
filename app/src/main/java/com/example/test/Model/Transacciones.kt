package com.example.test.Model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "transacciones",
    foreignKeys = [ForeignKey(
        entity = Cuentas::class,
        parentColumns = ["cuenta_id"],
        childColumns = ["cuenta_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Transacciones(
    @PrimaryKey(autoGenerate = true)
    val trasnsaccion_id: Int = 0,
    var tipo_trasnsaccion: String,
    var monto:Double,
    var fecha: String,
    val cuenta_id: Int,
    var estado : String

)
