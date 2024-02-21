package uk.co.maddwarf.notesinthenight.ui.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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

@Composable
fun InfoDialog(
    open: Boolean,
    onDismiss: () -> Unit,
    title: String,
    body: String
) {
    Log.d("INFO DIALOG", "$title $body")
    if (open) {
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
                      //  .background(color = Color.LightGray)
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TitleBlock(title = "", text = title)
                    TraitText(title = "", text = body)
                }
            }
        }
    }//end ifOpen
}//end InfoDialog
