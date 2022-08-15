package br.com.fiap.roomprof.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
class Game {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "titulo")
    var titulo: String = ""

    var estudio: String = ""

    @ColumnInfo(name = "valor_estimado", defaultValue = "1.0")
    var valorEstimado: Double = 0.0

    var finalizado: Boolean = false

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var foto: ByteArray? = null

}