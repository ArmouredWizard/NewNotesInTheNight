package uk.co.maddwarf.notesinthenight.model

data class Crew(
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
    val crewAbilities: List<CrewAbility> = listOf(),

    val contacts: List<ContactWithRating> = listOf(),

    val upgrades: List<CrewUpgrade> = listOf()
)
