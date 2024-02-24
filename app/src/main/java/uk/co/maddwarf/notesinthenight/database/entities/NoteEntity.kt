package uk.co.maddwarf.notesinthenight.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import uk.co.maddwarf.notesinthenight.model.Note

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val noteId:Int,
    var title:String = "",
    var body:String = ""
)

fun Note.toNoteEntity():NoteEntity = NoteEntity(
    noteId = noteId,
    title = title,
    body = body
)

fun NoteEntity.toNote():Note = Note(
    noteId = noteId,
    title = title,
    body = body
)


