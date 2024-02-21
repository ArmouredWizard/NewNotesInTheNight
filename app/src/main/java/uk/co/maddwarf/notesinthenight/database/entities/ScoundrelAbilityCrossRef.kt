package uk.co.maddwarf.notesinthenight.database.entities

import androidx.room.Entity

@Entity(primaryKeys = ["scoundrelId", "abilityId"])
data class ScoundrelAbilityCrossRef(
    val scoundrelId: Int,
    val abilityId: Int
)