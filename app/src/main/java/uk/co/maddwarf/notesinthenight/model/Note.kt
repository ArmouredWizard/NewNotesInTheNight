package uk.co.maddwarf.notesinthenight.model

data class Note(
    val noteId: Int = 0,
    var title: String = "",
    var category: String = "",
    var body: String = ""
)
