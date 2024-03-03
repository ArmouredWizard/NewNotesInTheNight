package uk.co.maddwarf.notesinthenight.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import uk.co.maddwarf.notesinthenight.model.Contact

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)
    val contactId: Int = 0,
    val contactName: String = "",
    val contactDescription: String = ""
)

fun ContactEntity.toContact(): Contact = Contact(
    id = contactId,
    name = contactName,
    description = contactDescription
)

fun Contact.toContactEntity(): ContactEntity = ContactEntity(
    contactId = id,
    contactName = name,
    contactDescription = description
)