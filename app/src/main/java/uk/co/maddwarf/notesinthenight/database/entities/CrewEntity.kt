package uk.co.maddwarf.notesinthenight.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import uk.co.maddwarf.notesinthenight.model.Crew

@Entity(tableName = "crews")
data class CrewEntity(
    @PrimaryKey(autoGenerate = true)
    val crewId: Int = 0,
    val crewName: String = "",
    val crewType: String = "",

    val hq: String = "",

    val rep: Int = 0,
    val tier: Int = 0,
    val heat: Int = 0,

    val holdIsStrong: Boolean = false,
    val reputation: String = "",
    val huntingGrounds: String = "",
)

fun Crew.toCrewEntity(): CrewEntity = CrewEntity(
    crewId = crewId,
    crewName = crewName,
    crewType = crewType,

    hq = hq.trim(),
    rep = rep,
    holdIsStrong = holdIsStrong,
    heat = heat,
    tier = tier,
    reputation = reputation.trim(),
    huntingGrounds = huntingGrounds.trim(),
)

fun CrewEntity.toCrew(): Crew = Crew(
    crewId = crewId,
    crewName = crewName,
    crewType = crewType,

    hq = hq.trim(),
    rep = rep,
    holdIsStrong = holdIsStrong,
    heat = heat,
    tier = tier,
    reputation = reputation.trim(),
    huntingGrounds = huntingGrounds.trim(),
)
