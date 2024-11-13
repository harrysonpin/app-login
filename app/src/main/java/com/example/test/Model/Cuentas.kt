package com.example.test.Model



import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "cuentas",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["user_id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )]
)

data class Cuentas(
    @PrimaryKey(autoGenerate = true)
    val cuenta_id: Int = 0,
    var tipo: String,
    var saldo: Double,
    val user_id: Int

)
