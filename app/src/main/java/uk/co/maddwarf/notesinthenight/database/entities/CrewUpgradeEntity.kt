package uk.co.maddwarf.notesinthenight.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import uk.co.maddwarf.notesinthenight.model.CrewUpgrade

@Entity(tableName = "crewupgrades")
data class CrewUpgradeEntity(
    @PrimaryKey(autoGenerate = true)
    val upgradeId: Int,
    val upgradeName: String,
    val upgradeDescription: String
)

fun CrewUpgradeEntity.toCrewUpgrade(): CrewUpgrade = CrewUpgrade(
    upgradeId = upgradeId,
    upgradeName = upgradeName,
    upgradeDescription = upgradeDescription
)

fun CrewUpgrade.toCrewUpgradeEntity(): CrewUpgradeEntity = CrewUpgradeEntity(
    upgradeId = upgradeId,
    upgradeName = upgradeName,
    upgradeDescription = upgradeDescription
)
