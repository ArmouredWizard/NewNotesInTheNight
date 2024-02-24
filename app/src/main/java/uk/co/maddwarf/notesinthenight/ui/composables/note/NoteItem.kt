package uk.co.maddwarf.notesinthenight.ui.composables.note

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.co.maddwarf.notesinthenight.model.Note

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier,
    displayDeleteNoteDialog: (Note) -> Unit,
    displayEditNoteDialog:(Note)->Unit,
    onClick: (Note) -> Unit,
    enableDelete: Boolean = true,
    enableEdit: Boolean = true
) {
    Card(
        modifier = modifier.fillMaxWidth(.9f),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .clickable { onClick(note) }
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row() {
                    Text(
                        text = note.title,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row() {
                    if (enableEdit) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "edit",
                            Modifier
                                .clickable { displayEditNoteDialog(note) } //todo
                                .align(Alignment.CenterVertically)
                                .size(25.dp)
                        )
                    }
                    if (enableDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            Modifier
                                .clickable { displayDeleteNoteDialog(note) }
                                .align(Alignment.CenterVertically)
                                .size(25.dp)
                        )
                    }
                }
            }
            Row {
                Text(
                    text = "Tags: ",
                    style = MaterialTheme.typography.bodySmall
                )
                note.tags.forEach{
                    Text(
                        text = "${it.tag} ,",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}