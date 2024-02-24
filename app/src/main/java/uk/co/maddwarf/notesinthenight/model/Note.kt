package uk.co.maddwarf.notesinthenight.model

data class Note(
    val noteId: Int = 0,
    var title: String = "",
    var tags: MutableList<Tag> = mutableListOf(),
    var body: String = ""
)
