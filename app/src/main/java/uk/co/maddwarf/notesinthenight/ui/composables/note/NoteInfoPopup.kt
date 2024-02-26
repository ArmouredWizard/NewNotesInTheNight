package uk.co.maddwarf.notesinthenight.ui.composables.note

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import uk.co.maddwarf.notesinthenight.ui.composables.TitleBlock
import uk.co.maddwarf.notesinthenight.ui.composables.TraitText

@Composable
fun NoteInfoPopUp(
    title:String,
    firstTextTitle:String,
    firstText:String,
    secondTextTitle:String,
    secondText:String,
    thirdTitle:String,
    thirdText:String,
    onDismiss: () -> Unit
) {
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
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleBlock(title = "", text = title)
                TraitText(title = firstTextTitle, text = firstText)
                TraitText(title = secondTextTitle, text = secondText)
                TraitText(title = thirdTitle, text = thirdText)
            }
        }
    }
}