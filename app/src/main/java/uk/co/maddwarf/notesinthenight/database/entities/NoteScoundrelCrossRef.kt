package uk.co.maddwarf.notesinthenight.database.entities

import androidx.room.Entity

@Entity(primaryKeys = ["noteId", "scoundrelId"])
data class NoteScoundrelCrossRef(
    val noteId: Int,
    val scoundrelId: Int
)