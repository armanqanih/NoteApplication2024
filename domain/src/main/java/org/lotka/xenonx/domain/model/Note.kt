package org.lotka.xenonx.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)

class InvalidNoteException(message: String): Exception(message)