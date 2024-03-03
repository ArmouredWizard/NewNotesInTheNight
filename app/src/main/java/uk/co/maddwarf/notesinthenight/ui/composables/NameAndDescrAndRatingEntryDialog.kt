package uk.co.maddwarf.notesinthenight.ui.composables

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import uk.co.maddwarf.notesinthenight.model.NameDescrRatingResult

@Composable
fun NameAndDescrAndRatingEntryDialog(
    title: String,
    name: String,
    nameLabel: String,
    nameHint: String,
    description: String,
    descriptionLabel: String,
    descriptionHint: String,
    rating: Int = 0,
    ratingLabel: String = "Rating",
    onDismiss: () -> Unit,
    onAccept: (NameDescrRatingResult) -> Unit,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onRatingChange: (Int) -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 2.dp, color = Color.DarkGray, shape = RoundedCornerShape(20.dp))
                .clip(shape = RoundedCornerShape(20.dp))
                .paint(
                    painterResource(id = R.drawable.cobbles),
                    contentScale = ContentScale.FillBounds
                )
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

                Spacer(modifier = Modifier.height(5.dp))

                var badBorderWidth = 1.dp
                var neutralBorderWidth = 1.dp
                var goodBorderWidth = 1.dp
                var badBorderColor = Color.Black
                var goodBorderColor = Color.Black
                var neutralBorderColor = Color.Black

                when (rating) {
                    -1 -> {
                        badBorderWidth = 1.dp
                        neutralBorderWidth = 0.dp
                        goodBorderWidth = 0.dp
                        badBorderColor = Color.Black
                        goodBorderColor = MaterialTheme.colorScheme.primaryContainer
                        neutralBorderColor = MaterialTheme.colorScheme.primaryContainer
                    }

                    0 -> {
                        badBorderWidth = 0.dp
                        neutralBorderWidth = 1.dp
                        goodBorderWidth = 0.dp
                        neutralBorderColor = Color.Black
                        goodBorderColor = MaterialTheme.colorScheme.primaryContainer
                        badBorderColor = MaterialTheme.colorScheme.primaryContainer
                    }

                    1 -> {
                        badBorderWidth = 0.dp
                        neutralBorderWidth = 0.dp
                        goodBorderWidth = 1.dp
                        goodBorderColor = Color.Black
                        badBorderColor = MaterialTheme.colorScheme.primaryContainer
                        neutralBorderColor = MaterialTheme.colorScheme.primaryContainer
                    }
                }

                Row(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(5.dp))
                        .fillMaxWidth()
                        .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(5.dp))
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = ratingLabel)
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Bad",
                        tint = Color.Red,
                        modifier = Modifier
                            .clickable { onRatingChange(-1) }
                            .border(width = badBorderWidth, color = badBorderColor)
                            .padding(5.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Neutral",
                        tint = Color.DarkGray,
                        modifier = Modifier
                            .clickable { onRatingChange(0) }
                            .border(width = neutralBorderWidth, color = neutralBorderColor)
                            .padding(5.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Good",
                        tint = Color.Green,
                        modifier = Modifier
                            .clickable { onRatingChange(1) }
                            .border(width = goodBorderWidth, color = goodBorderColor)
                            .padding(5.dp))
                }

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
                                NameDescrRatingResult(
                                    name,
                                    description,
                                    rating
                                )
                            )
                        }, text = "Accept")
                    }
                }//end button Row
            }//end column
        }//end surface
    }//end dialog
}//end name and descr EntryDialog