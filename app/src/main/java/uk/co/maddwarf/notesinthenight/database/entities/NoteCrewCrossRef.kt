package uk.co.maddwarf.notesinthenight.database.entities

import androidx.room.Entity

@Entity(primaryKeys = ["noteId", "crewId"])
data class NoteCrewCrossRef(
    val noteId: Int,
    val crewId: Int
)
