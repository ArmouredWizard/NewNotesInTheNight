package uk.co.maddwarf.notesinthenight.model

data class Note(
    val noteId: Int = 0,
    var title: String = "",
    var body: String = "",
    var tags: MutableList<Tag> = mutableListOf(),
    var scoundrels: MutableList<Scoundrel> = mutableListOf(),
    var crews: MutableList<Crew> = mutableListOf()
)
