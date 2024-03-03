package uk.co.maddwarf.notesinthenight.database.entities

import androidx.room.DatabaseView
import uk.co.maddwarf.notesinthenight.model.ContactWithRating

@DatabaseView(
    "SELECT * from contacts inner join CrewContactCrossRef on contacts.contactId = CrewContactCrossRef.contactId"
)
data class CrewContactWithRatingView(
    val crewId:Int = 0,
    var contactId:Int = 0,
    val contactName:String = "",
    var contactDescription: String = "",
    var rating: Int = 0
)

fun CrewContactWithRatingView.toContactWithRating():ContactWithRating = ContactWithRating(
    contactId = contactId,
    contactName = contactName,
    contactDescription = contactDescription,
    rating = rating
)