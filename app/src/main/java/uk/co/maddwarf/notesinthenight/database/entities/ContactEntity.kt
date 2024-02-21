package uk.co.maddwarf.notesinthenight.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import uk.co.maddwarf.notesinthenight.model.Contact

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)
    val contactId: Int = 0,
    val name: String = "",
    val description: String = ""
)

fun ContactEntity.toContact(): Contact = Contact(
    id = contactId,
    name = name,
    description = description
)

fun Contact.toContactEntity(): ContactEntity = ContactEntity(
    contactId = id,
    name = name,
    description = description
)