package uk.co.maddwarf.notesinthenight.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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

@Composable
fun NameAndDescrEntryDialog(
    title:String,
    name: String,
    nameLabel:String,
    nameHint:String,
    description: String,
    descriptionLabel:String,
    descriptionHint:String,
    onDismiss: () -> Unit,
    onAccept: (Pair<String, String>) -> Unit,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 2.dp, color = Color.DarkGray, shape = RoundedCornerShape(20.dp))
                .clip(shape = RoundedCornerShape(20.dp))
                .paint(painterResource(id = R.drawable.cobbles), contentScale = ContentScale.FillBounds)
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleBlock(title = "", text = title)
                TextEntryRowWithInfoIcon(
                    data = name,
                    onValueChange = onNameChange,
                    label = nameLabel,
                    infoText = nameHint
                )
                TextEntryRowWithInfoIcon(
                    singleLine = false,
                    data = description,
                    onValueChange = onDescriptionChange,
                    label = descriptionLabel,
                    infoText = descriptionHint
                )

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
                        MyButton(onClick = { onAccept(Pair(name, description)) }, text = "Accept")
                    }
                }//end button Row
            }//end column
        }//end surface
    }//end dialog
}//end name and descr EntryDialog