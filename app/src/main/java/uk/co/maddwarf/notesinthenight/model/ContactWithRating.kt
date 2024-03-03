package uk.co.maddwarf.notesinthenight.model

import uk.co.maddwarf.notesinthenight.database.entities.ContactEntity

data class ContactWithRating(
    val scoundrelId: Int = 0,
    val contactId: Int = 0,
    var contactName: String = "",
    var contactDescription: String = "",
    var rating: Int = 0
)


