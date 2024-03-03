package uk.co.maddwarf.notesinthenight.model

data class ContactWithRating(
    val connectionId: Int = 0,
    val contactId: Int = 0,
    var contactName: String = "",
    var contactDescription: String = "",
    var rating: Int = 0
)


