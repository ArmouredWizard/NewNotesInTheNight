package uk.co.maddwarf.notesinthenight.ui.screens.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import uk.co.maddwarf.notesinthenight.R
import uk.co.maddwarf.notesinthenight.model.Crew
import uk.co.maddwarf.notesinthenight.model.Note
import uk.co.maddwarf.notesinthenight.model.Scoundrel
import uk.co.maddwarf.notesinthenight.model.Tag
import uk.co.maddwarf.notesinthenight.ui.composables.TextEntryRowWithInfoIcon
import uk.co.maddwarf.notesinthenight.ui.composables.TitleBlock
import uk.co.maddwarf.notesinthenight.ui.composables.crew.MyCrewSpinner
import uk.co.maddwarf.notesinthenight.ui.composables.note.MyTagSpinner
import uk.co.maddwarf.notesinthenight.ui.composables.scoundrel.MyScoundrelSpinner

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NoteInputForm(
    title: String = "New Note",
    noteDetails: Note,
    onValueChange: (Note) -> Unit,
    newTag: String,
    tagsList: List<Tag>,
    everyScoundrelList: List<Scoundrel>,
    everyCrewList: List<Crew>,
    onNewTagChange: (String) -> Unit
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
        //    .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TitleBlock(title = "", text = title)
            TextEntryRowWithInfoIcon(
                data = noteDetails.title,
                onValueChange = { onValueChange(noteDetails.copy(title = it)) },
                label = "Title",
                infoText = "Note Title"
            )
            TextEntryRowWithInfoIcon(
                singleLine = false,
                data = noteDetails.body,
                onValueChange = { onValueChange(noteDetails.copy(body = it)) },
                label = "Note Text",
                infoText = "Note Body Text"
            )
//end Note Title and Body

            Spacer(modifier = Modifier.height(5.dp))
            Column(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(5.dp))
                    .border(width = 1.dp, shape = RoundedCornerShape(5.dp), color = Color.Black)
                    .background(color = Color.LightGray)
                    .padding(10.dp)
            ) {

                Text(text = "TAGS")
                FlowRow {
                    noteDetails.tags.forEach {
                        Text(
                            text = it.tag,
                            modifier = Modifier
                                .padding(3.dp)
                                .background(color = MaterialTheme.colorScheme.primaryContainer)
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(modifier = Modifier.weight(0.85f)) {
                        TextEntryRowWithInfoIcon(
                            data = newTag,
                            onValueChange = onNewTagChange,
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
                                .clickable(onClick = {
                                    onValueChange(
                                        noteDetails.copy(
                                            tags = (noteDetails.tags + Tag(
                                                tag = newTag
                                            )).toMutableList()
                                        )
                                    )
                                }
                                )
                                .size(30.dp)
                        )
                    }
                }

                var tagExpanded by remember { mutableStateOf(false) }
                var chosenExistingTag by remember { mutableStateOf(Tag()) }
                fun tagChooser(tag: Tag) {
                    tagExpanded = false
                    chosenExistingTag = tag
                    onValueChange(
                        noteDetails.copy(
                            tags = (noteDetails.tags + chosenExistingTag).toMutableList()
                        )
                    )
                    //  onTagAdd(chosenExistingTag)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Add Existing:")
                    }
                    Row(
                        modifier = Modifier.weight(1f)
                    ) {
                        MyTagSpinner(
                            expanded = tagExpanded,
                            onClick = { tagExpanded = !tagExpanded },
                            list = tagsList,
                            chooser = ::tagChooser,
                            report = "Tag"
                        )
                    }
                }
            }//end TAGS column

            Column(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(5.dp))
                    .border(width = 1.dp, shape = RoundedCornerShape(5.dp), color = Color.Black)
                    .background(color = Color.LightGray)
                    .padding(10.dp)
            ) {
                Text(text = "Scoundrels")
                /* LazyRow {
                     items(newScoundrels) {
                         Text(text = it.name)
                     }
                 }*/

                FlowRow {
                    noteDetails.scoundrels.forEach {
                        Text(
                            text = it.name,
                            modifier = Modifier
                                .padding(3.dp)
                                .background(color = MaterialTheme.colorScheme.primaryContainer)
                        )
                    }
                }

                var scoundrelExpanded by remember { mutableStateOf(false) }
                var chosenExistingScoundrel by remember { mutableStateOf(Scoundrel()) }
                fun scoundrelChooser(scoundrel: Scoundrel) {
                    scoundrelExpanded = false
                    chosenExistingScoundrel = scoundrel
                    onValueChange(
                        noteDetails.copy(scoundrels = (noteDetails.scoundrels + chosenExistingScoundrel).toMutableList())
                    )
//                    onScoundrelAdd(chosenExistingScoundrel)
                }
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Add Existing:")
                    }
                    Row(
                        modifier = Modifier.weight(1f)
                    ) {
                        MyScoundrelSpinner(
                            expanded = scoundrelExpanded,
                            onClick = { scoundrelExpanded = !scoundrelExpanded },
                            list = everyScoundrelList,
                            chooser = ::scoundrelChooser,
                            report = "Scoundrel"
                        )
                    }
                }
            }//end scoundrel Column

            Column(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(5.dp))
                    .border(width = 1.dp, shape = RoundedCornerShape(5.dp), color = Color.Black)
                    .background(color = Color.LightGray)
                    .padding(10.dp)
            ) {

                Text(text = "Crew")
                /*  LazyRow {
                      items(newCrews) {
                          Text(text = it.crewName)
                      }
                  }*/

                FlowRow {
                    noteDetails.crews.forEach {
                        Text(
                            text = it.crewName,
                            modifier = Modifier
                                .padding(3.dp)
                                .background(color = MaterialTheme.colorScheme.primaryContainer)
                        )
                    }
                }

                var crewExpanded by remember { mutableStateOf(false) }
                var chosenExistingCrew by remember { mutableStateOf(Crew()) }
                fun crewChooser(crew: Crew) {
                    crewExpanded = false
                    chosenExistingCrew = crew
                    onValueChange(
                        noteDetails.copy(crews = (noteDetails.crews + chosenExistingCrew).toMutableList())
                    )
                    // onCrewAdd(chosenExistingCrew)
                }
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Add Existing:")
                    }
                    Row(
                        modifier = Modifier.weight(1f)
                    ) {
                        MyCrewSpinner(
                            expanded = crewExpanded,
                            onClick = { crewExpanded = !crewExpanded },
                            list = everyCrewList,
                            chooser = ::crewChooser,
                            report = "Crew"
                        )
                    }
                }
            }//end crew column

        }//end column
    }//end box
}//end Note Input Form