package uk.co.maddwarf.notesinthenight.database.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import uk.co.maddwarf.notesinthenight.model.Note

data class FullNoteEntity(
    @Embedded val noteEntity: NoteEntity,

    @Relation(
        parentColumn = "noteId",
        entityColumn = "tagId",
        associateBy = Junction(NoteTagCrossRef::class)
    )
    val tags: List<TagEntity>
)

fun Note.toFullNoteEntity(): NoteEntity = NoteEntity(
    noteId = noteId,
    title = title,
    body = body
)

fun FullNoteEntity.toNote(): Note = Note(
    noteId = noteEntity.noteId,
    title = noteEntity.title,
    tags = tags.map {
        it.toTag()
    }.toMutableList(),
    body = noteEntity.body
)
