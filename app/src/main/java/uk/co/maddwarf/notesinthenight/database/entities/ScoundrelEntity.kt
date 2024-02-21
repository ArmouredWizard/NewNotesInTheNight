package uk.co.maddwarf.notesinthenight.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import uk.co.maddwarf.notesinthenight.model.Crew
import uk.co.maddwarf.notesinthenight.model.Scoundrel

@Entity(tableName = "scoundrels")
data class ScoundrelEntity(
    @PrimaryKey(autoGenerate = true)
    val scoundrelId: Int = 0,
    val name: String = "",

    val playbook: String = "",
    var heritage: String = "",
    var background: String = "",

    val hunt: Int = 0,
    val study: Int = 0,
    val survey: Int = 0,
    val tinker: Int = 0,
    val finesse: Int = 0,
    val prowl: Int = 0,
    val skirmish: Int = 0,
    val wreck: Int = 0,
    val attune: Int = 0,
    val command: Int = 0,
    val consort: Int = 0,
    val sway: Int = 0,

    val coin: Int = 0,

    val insightXp: Int = 0,
    val prowessXp: Int = 0,
    val resolveXp: Int = 0,
    val playbookXp: Int = 0,

    val crewId: Int?
)

fun ScoundrelEntity.toScoundrel(): Scoundrel = Scoundrel(
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

    crew = Crew()
)

fun Scoundrel.toScoundrelEntity(): ScoundrelEntity = ScoundrelEntity(
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

    crewId = crew?.crewId
)

