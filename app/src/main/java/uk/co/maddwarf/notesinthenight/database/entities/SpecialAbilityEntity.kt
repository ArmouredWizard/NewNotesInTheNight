package uk.co.maddwarf.notesinthenight.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import uk.co.maddwarf.notesinthenight.model.SpecialAbility

@Entity(tableName = "specialabilities")
data class SpecialAbilityEntity(
    @PrimaryKey(autoGenerate = true)
    val abilityId: Int = 0,
    val abilityName: String = "",
    val abilityDescription: String = ""
)

fun SpecialAbilityEntity.toSpecialAbility(): SpecialAbility = SpecialAbility(
    abilityId = abilityId,
    abilityName = abilityName,
    abilityDescription = abilityDescription
)

fun SpecialAbility.toSpecialAbilityEntity(): SpecialAbilityEntity = SpecialAbilityEntity(
    abilityId = abilityId,
    abilityName = abilityName,
    abilityDescription = abilityDescription
)