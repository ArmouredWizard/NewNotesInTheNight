package uk.co.maddwarf.notesinthenight.model

data class Scoundrel(
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

    val specialAbilities: List<SpecialAbility> = listOf(),

    var contacts: List<ContactWithRating> = listOf(),

    var crew: Crew? = null
)
