package uk.co.maddwarf.notesinthenight.model

import uk.co.maddwarf.notesinthenight.database.entities.ContactEntity

data class Contact(
    val id: Int = 0,
    var name: String = "",
    var description: String = ""
)

/*fun Contact.toContactEntity():ContactEntity = ContactEntity(
    contactId = id,
    contactName = name,
    contactDescription = description
)*/

fun Contact.toContactWithRating():ContactWithRating = ContactWithRating(
    contactId = id,
    contactName = name,
    contactDescription = description,
    rating = 0
)
