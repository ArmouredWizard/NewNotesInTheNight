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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
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
import uk.co.maddwarf.notesinthenight.model.Crew
import uk.co.maddwarf.notesinthenight.model.Note
import uk.co.maddwarf.notesinthenight.model.Scoundrel
import uk.co.maddwarf.notesinthenight.model.Tag
import uk.co.maddwarf.notesinthenight.ui.composables.MyButton
import uk.co.maddwarf.notesinthenight.ui.composables.TextEntryRowWithInfoIcon
import uk.co.maddwarf.notesinthenight.ui.composables.TextEntryWithSpinner
import uk.co.maddwarf.notesinthenight.ui.composables.TitleBlock
import uk.co.maddwarf.notesinthenight.ui.composables.crew.MyCrewSpinner
import uk.co.maddwarf.notesinthenight.ui.composables.scoundrel.MyScoundrelSpinner

@Composable
fun NoteEntryDialog(
    noteId: Int,
    title: String,
    body: String,
    tags: List<Tag>,
    newTag: String,

    scoundrels: List<Scoundrel>,
    crews: List<Crew>,

    onDismiss: () -> Unit,
    onAccept: (Note) -> Unit,
    onTitleChange: (String) -> Unit,
    onBodyChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    tagsList: List<Tag>,
    onTagAdd: (Tag) -> Unit,
    everyScoundrelList: List<Scoundrel>,
    onScoundrelAdd: (Scoundrel) -> Unit,
    everyCrewList: List<Crew>,
    onCrewAdd: (Crew) -> Unit
) {

    val newTags: MutableList<Tag> = tags.distinct().toMutableList()
    val newScoundrels: MutableList<Scoundrel> = scoundrels.distinct().toMutableList()
    val newCrews: MutableList<Crew> = crews.distinct().toMutableList()

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

//todo FORMAT TAGS
                Text(text = "TAGS")

                LazyRow {
                    items(newTags) {
                        Text(text = it.tag)
                    }
                }
//TODO FORMAT TAGS

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(modifier = Modifier.weight(0.85f)) {
                        TextEntryRowWithInfoIcon(
                            data = newTag,
                            onValueChange = onCategoryChange,
                            label = "New Tag",
                            infoText = "Enter New Tag. Make sure to click the ADD Button before Accepting the Note",
                        )
                    }
                    Row(
                        modifier = Modifier
                            .weight(0.15f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = "Add Tag",
                            modifier = Modifier
                                .clickable(onClick = { onTagAdd(Tag(tag = newTag)) })
                                .size(30.dp)
                            )
                    }
                }

                var tagExpanded by remember { mutableStateOf(false) }
                var chosenExistingTag by remember { mutableStateOf(Tag()) }
                fun tagChooser(tag: Tag) {
                    tagExpanded = false
                    chosenExistingTag = tag
                    onTagAdd(chosenExistingTag)
                }
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Add a Tag")
                    MyTagSpinner(
                        expanded = tagExpanded,
                        onClick = { tagExpanded = !tagExpanded },
                        list = tagsList,
                        chooser = ::tagChooser,
                        report = "Tag"
                    )
                }

                Text(text = "Scoundrels")
                LazyRow {
                    items(newScoundrels) {
                        Text(text = it.name)
                    }
                }

                var scoundrelExpanded by remember { mutableStateOf(false) }
                var chosenExistingScoundrel by remember { mutableStateOf(Scoundrel()) }
                fun scoundrelChooser(scoundrel: Scoundrel) {
                    scoundrelExpanded = false
                    chosenExistingScoundrel = scoundrel
                    onScoundrelAdd(chosenExistingScoundrel)
                }
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Add a Scoundrel")
                    MyScoundrelSpinner(
                        expanded = scoundrelExpanded,
                        onClick = { scoundrelExpanded = !scoundrelExpanded },
                        list = everyScoundrelList,
                        chooser = ::scoundrelChooser,
                        report = "Scoundrel"
                    )
                }

                Text(text = "Crew")
                LazyRow {
                    items(newCrews) {
                        Text(text = it.crewName)
                    }
                }

                var crewExpanded by remember { mutableStateOf(false) }
                var chosenExistingCrew by remember { mutableStateOf(Crew()) }
                fun crewChooser(crew: Crew) {
                    crewExpanded = false
                    chosenExistingCrew = crew
                    onCrewAdd(chosenExistingCrew)
                }
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Add a Crew")
                    MyCrewSpinner(
                        expanded = crewExpanded,
                        onClick = { crewExpanded = !crewExpanded },
                        list = everyCrewList,
                        chooser = ::crewChooser,
                        report = "Crew"
                    )
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
                                    tags = newTags,
                                    scoundrels = newScoundrels
                                )
                            )
                        }, text = "Accept")
                    }

                }//end main button row
            }//end column
        }//end Box
    }//end Dialog
}//end Note Entry Dialog