package uk.co.maddwarf.notesinthenight.database.entities

import android.util.Log
import androidx.room.DatabaseView
import uk.co.maddwarf.notesinthenight.model.Contact
import uk.co.maddwarf.notesinthenight.model.ContactWithRating

@DatabaseView(
    "SELECT * from contacts" +
            " inner join ScoundrelContactCrossRef" +
            " on contacts.contactId = ScoundrelContactCrossRef.contactId"
)
data class ContactWithRatingView(
    val scoundrelId: Int = 0,
    var contactId: Int,
    val contactName: String,
    var contactDescription: String,
    var rating: Int = 0
)

fun ContactWithRatingView.toContactWithRating(): ContactWithRating {
    Log.d("CONTACT to convert", contactName)
    return ContactWithRating(
        contactId = contactId,
        contactName = contactName,
        contactDescription = contactDescription,
        rating = rating
    )
}

fun ContactWithRating.toContactEntity(): ContactEntity = ContactEntity(
    contactId = contactId,
    contactName = contactName,
    contactDescription = contactDescription
)