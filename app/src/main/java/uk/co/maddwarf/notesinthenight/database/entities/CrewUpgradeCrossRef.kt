package uk.co.maddwarf.notesinthenight.database.entities

import androidx.room.Entity

@Entity(primaryKeys = ["crewId", "upgradeId"])
data class CrewUpgradeCrossRef(
    val crewId: Int,
    val upgradeId: Int
)