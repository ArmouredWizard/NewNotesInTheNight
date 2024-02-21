package uk.co.maddwarf.notesinthenight.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import uk.co.maddwarf.notesinthenight.model.CrewAbility

@Entity(tableName = "crewabilities")
data class CrewAbilityEntity(
    @PrimaryKey(autoGenerate = true)
    val crewAbilityId: Int = 0,
    val crewAbilityName: String = "",
    val crewAbilityDescription: String = ""
)

fun CrewAbilityEntity.toCrewAbility(): CrewAbility = CrewAbility(
    crewAbilityId = crewAbilityId,
    crewAbilityName = crewAbilityName,
    crewAbilityDescription = crewAbilityDescription
)

fun CrewAbility.toCrewAbilityEntity(): CrewAbilityEntity = CrewAbilityEntity(
    crewAbilityId = crewAbilityId,
    crewAbilityName = crewAbilityName,
    crewAbilityDescription = crewAbilityDescription
)