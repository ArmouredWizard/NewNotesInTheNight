package uk.co.maddwarf.notesinthenight.database.entities

import androidx.room.Entity

@Entity(primaryKeys = ["crewId", "contactId"])
data class CrewContactCrossRef(
    val crewId: Int,
    val contactId: Int
)
