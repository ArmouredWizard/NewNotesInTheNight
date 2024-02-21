package uk.co.maddwarf.notesinthenight.database.entities

import androidx.room.Entity

@Entity(primaryKeys = ["scoundrelId", "contactId"])
data class ScoundrelContactCrossRef(
    val scoundrelId: Int,
    val contactId: Int
)