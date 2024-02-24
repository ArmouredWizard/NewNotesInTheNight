package uk.co.maddwarf.notesinthenight.ui.composables.note

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import uk.co.maddwarf.notesinthenight.R
import uk.co.maddwarf.notesinthenight.model.Note
import uk.co.maddwarf.notesinthenight.model.Tag
import uk.co.maddwarf.notesinthenight.ui.composables.MyButton
import uk.co.maddwarf.notesinthenight.ui.composables.TextEntryRowWithInfoIcon
import uk.co.maddwarf.notesinthenight.ui.composables.TextEntryWithSpinner
import uk.co.maddwarf.notesinthenight.ui.composables.TitleBlock

@Composable
fun NoteEntryDialog(
    noteId:Int,
    title: String,
    body: String,
    tags: List<Tag>,
    newTag: String,
    onDismiss: () -> Unit,
    onAccept: (Note) -> Unit,
    onTitleChange: (String) -> Unit,
    onBodyChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    tagsList: List<Tag>,
    onTagAdd:(String)->Unit,
) {

    var newTags: MutableList<Tag> = tags.distinct().toMutableList()
    var localNewtag by remember{ mutableStateOf( newTag)}

   /* fun onTagAdd(newTag: String) {
        newTags.add(Tag(tag = newTag))
        localNewtag = ""
    }*/


    Dialog(
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(20.dp))
                .clip(shape = RoundedCornerShape(20.dp))
                .paint(
                    painterResource(id = R.drawable.cobbles),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TitleBlock(title = "", text = "New Note")
                TextEntryRowWithInfoIcon(
                    data = title,
                    onValueChange = onTitleChange,
                    label = "Title",
                    infoText = "Note Title"
                )
                TextEntryRowWithInfoIcon(
                    singleLine = false,
                    data = body,
                    onValueChange = onBodyChange,
                    label = "Note Text",
                    infoText = "Note Body Text"
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    /*        TextEntryWithSpinner(
                                textValue = newtag,
                                label = "Category",
                                infoText = "Enter a Category for this Note",
                                itemList = tagsList,
                                onValueChange = onCategoryChange
                            )*/
                }

                Text(text = "TAGS")
                LazyRow {
                    items(newTags) {
                        Text(text = it.tag)
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(modifier = Modifier.weight(0.8f)) {
                        TextEntryWithSpinner(
                            textValue = newTag,//localNewtag,
                            onValueChange = onCategoryChange,
                            label = "New Tag",
                            infoText = "Enter New Tag, or select from List. Make sure to click the ADD Button before Accepting the Note",
                            itemList = tagsList.map{
                                it.tag
                            }
                        )
                    }
                    Row(modifier = Modifier.weight(0.2f)) {
                        Icon(
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = "Add Tag",
                            modifier = Modifier.clickable(onClick = { onTagAdd(newTag) })
                        )
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        MyButton(onClick = onDismiss, text = "Cancel")
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        MyButton(onClick = {
                            onAccept(
                                Note(
                                    noteId = noteId,
                                    title = title,
                                    body = body,
                                    tags = newTags
                                )
                            )
                        }, text = "Accept")
                    }

                }//end main button row
            }//end column
        }//end Box
    }//end Dialog
}//end Note Entry Dialog