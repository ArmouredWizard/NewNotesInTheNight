package uk.co.maddwarf.notesinthenight.database.entities

import androidx.room.Entity

@Entity(primaryKeys = ["crewId", "crewAbilityId"])
data class CrewAbilityCrossRef(
    val crewId: Int,
    val crewAbilityId: Int
)