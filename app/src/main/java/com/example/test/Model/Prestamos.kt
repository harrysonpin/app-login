package com.example.test.Model



import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "prestamos",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["user_id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )]
)

data class Prestamos(
    @PrimaryKey(autoGenerate = true)
    val prestamo_id: Int = 0,
    val monto: Double,
    var tasa_interes: Double,
    val plazo: Int,
    val user_id: Int

)
