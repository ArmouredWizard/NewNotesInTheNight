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
    val tags: List<TagEntity>,

    @Relation(
        parentColumn = "noteId",
        entityColumn = "scoundrelId",
        associateBy = Junction(NoteScoundrelCrossRef::class)
    )
    val scoundrels: List<ScoundrelEntity>,

    @Relation(
        parentColumn = "noteId",
        entityColumn = "crewId",
        associateBy = Junction(NoteCrewCrossRef::class)
    )
    val crews: List<CrewEntity>
)

fun Note.toFullNoteEntity(): NoteEntity = NoteEntity(
    noteId = noteId,
    title = title,
    body = body,
)

fun FullNoteEntity.toNote(): Note = Note(
    noteId = noteEntity.noteId,
    title = noteEntity.title,
    body = noteEntity.body,
    tags = tags.map {
        it.toTag()
    }.toMutableList(),
    scoundrels = scoundrels.map {
        it.toScoundrel()
    }.toMutableList(),
    crews = crews.map {
        it.toCrew()
    }.toMutableList()
)
