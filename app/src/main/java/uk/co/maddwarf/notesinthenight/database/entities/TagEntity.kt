package uk.co.maddwarf.notesinthenight.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import uk.co.maddwarf.notesinthenight.model.Tag

@Entity(tableName = "tags")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val tagId: Int = 0,
    val tag:String = ""
)

fun TagEntity.toTag(): Tag = Tag(
    tagId = tagId,
    tag = tag
)
fun Tag.toTagEntity(): TagEntity = TagEntity(
    tagId = tagId,
    tag = tag
)
