package uk.co.maddwarf.notesinthenight.database.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import uk.co.maddwarf.notesinthenight.model.Scoundrel

data class FullScoundrelEntity(
    @Embedded val scoundrelEntity: ScoundrelEntity,

    @Relation(
        parentColumn = "crewId",
        entityColumn = "crewId"
    )
    val crew: CrewEntity?,

    @Relation(
        parentColumn = "scoundrelId",
        entityColumn = "abilityId",
        associateBy = Junction(ScoundrelAbilityCrossRef::class)
    )
    val specialabilities: List<SpecialAbilityEntity>,

    @Relation(
        parentColumn = "scoundrelId",
        entityColumn = "contactId",
        associateBy = Junction(ScoundrelContactCrossRef::class)
    )
    val contacts: List<ContactEntity>
)

fun FullScoundrelEntity.toScoundrel(): Scoundrel = Scoundrel(
    scoundrelId = scoundrelEntity.scoundrelId,
    name = scoundrelEntity.name,

    playbook = scoundrelEntity.playbook,
    heritage = scoundrelEntity.heritage,
    background = scoundrelEntity.background,

    hunt = scoundrelEntity.hunt,
    study = scoundrelEntity.study,
    survey = scoundrelEntity.survey,
    tinker = scoundrelEntity.tinker,
    finesse = scoundrelEntity.finesse,
    prowl = scoundrelEntity.prowl,
    skirmish = scoundrelEntity.skirmish,
    wreck = scoundrelEntity.wreck,
    attune = scoundrelEntity.attune,
    command = scoundrelEntity.command,
    consort = scoundrelEntity.consort,
    sway = scoundrelEntity.sway,

    coin = scoundrelEntity.coin,

    insightXp = scoundrelEntity.insightXp,
    prowessXp = scoundrelEntity.prowessXp,
    resolveXp = scoundrelEntity.resolveXp,
    playbookXp = scoundrelEntity.playbookXp,

    crew = crew?.toCrew(),

    specialAbilities = specialabilities.map {
        it.toSpecialAbility()
    },

    contacts = contacts.map {
        it.toContact()
    }
)

fun Scoundrel.toFullScoundrelEntity(): ScoundrelEntity = ScoundrelEntity(
    scoundrelId = scoundrelId,
    name = name,

    playbook = playbook,
    heritage = heritage,
    background = background,

    hunt = hunt,
    study = study,
    survey = survey,
    tinker = tinker,
    finesse = finesse,
    prowl = prowl,
    skirmish = skirmish,
    wreck = wreck,
    attune = attune,
    command = command,
    consort = consort,
    sway = sway,

    coin = coin,

    insightXp = insightXp,
    prowessXp = prowessXp,
    resolveXp = resolveXp,
    playbookXp = playbookXp,

    crewId = crew?.crewId ?: 0


)
