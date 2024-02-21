package uk.co.maddwarf.notesinthenight.ui.composables.crew

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import uk.co.maddwarf.notesinthenight.model.Crew
import uk.co.maddwarf.notesinthenight.ui.composables.MyButton
import uk.co.maddwarf.notesinthenight.ui.composables.TitleBlock
import uk.co.maddwarf.notesinthenight.ui.composables.TraitText

@Composable
fun CrewInfoDialog(
    crew: Crew,
    onDismiss: () -> Unit,
    onAccept: (Crew) -> Unit
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
                TitleBlock(title = "", text = "Crew:")
                TraitText(title = "Crew Name", text = crew.crewName)
                TraitText(title = "Crew Type", text = crew.crewType)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        MyButton(onClick = onDismiss, text = "Close")
                    }
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        MyButton(onClick = { onAccept(crew) }, text = "Details...")
                    }
                }
            }//end column
        }//end surface
    }//end dialog
}