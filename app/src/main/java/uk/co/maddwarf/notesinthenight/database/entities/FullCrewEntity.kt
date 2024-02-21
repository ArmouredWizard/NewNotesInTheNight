package uk.co.maddwarf.notesinthenight.database.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import uk.co.maddwarf.notesinthenight.model.Crew

data class FullCrewEntity(
    @Embedded val crewEntity: CrewEntity,

    @Relation(
        parentColumn = "crewId",
        entityColumn = "crewAbilityId",
        associateBy = Junction(CrewAbilityCrossRef::class)
    )
    val crewabilities: List<CrewAbilityEntity>,


    @Relation(
        parentColumn = "crewId",
        entityColumn = "contactId",
        associateBy = Junction(CrewContactCrossRef::class)
    )
    val crewcontacts: List<ContactEntity>,

    @Relation(
        parentColumn = "crewId",
        entityColumn = "upgradeId",
        associateBy = Junction(CrewUpgradeCrossRef::class)
    )
    val crewupgrades: List<CrewUpgradeEntity>,

    @Relation(
        parentColumn = "crewId",
        entityColumn = "scoundrelId",
    )
    val scoundrels: List<ScoundrelEntity> = listOf()
)


fun FullCrewEntity.toCrew(): Crew = Crew(
    crewId = crewEntity.crewId,
    crewName = crewEntity.crewName,
    crewType = crewEntity.crewType,

    hq = crewEntity.hq.trim(),
    rep = crewEntity.rep,
    holdIsStrong = crewEntity.holdIsStrong,
    heat = crewEntity.heat,
    tier = crewEntity.tier,
    reputation = crewEntity.reputation.trim(),
    huntingGrounds = crewEntity.huntingGrounds.trim(),
    crewAbilities = crewabilities.map {
        it.toCrewAbility()
    },
    contacts = crewcontacts.map {
        it.toContact()
    },
    upgrades = crewupgrades.map {
        it.toCrewUpgrade()
    }

)

fun Crew.toFullCrewEntity(): FullCrewEntity =
    FullCrewEntity(
        crewEntity = CrewEntity(
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

            ),
        crewabilities = crewAbilities.map {
            it.toCrewAbilityEntity()
        },
        crewcontacts = contacts.map {
            it.toContactEntity()
        },
        crewupgrades = upgrades.map {
            it.toCrewUpgradeEntity()
        }
    )

