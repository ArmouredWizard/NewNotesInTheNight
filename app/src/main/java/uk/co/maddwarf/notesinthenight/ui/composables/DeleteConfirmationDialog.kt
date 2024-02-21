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
import androidx.compose.material3.Text
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
fun DeleteConfirmationDialog(
    title:String,
    name:String,
    onAccept:()->Unit,
    onDismiss:()->Unit,
    deleteType:String = "Permanently Delete"
){
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = Modifier
                .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(20.dp))
                .clip(shape = RoundedCornerShape(20.dp))
                .paint(painterResource(id = R.drawable.cobbles), contentScale = ContentScale.FillBounds)
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleBlock(title = "", text = name)
                TraitText(title = "", text = "Are you sure you wish to $deleteType this $title?")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MyButton(
                        onClick = onDismiss,
                        text = "No",
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    MyButton(
                        onClick = { onAccept() },
                        text = "Yes",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }//end surface
    }//end dialog
}//end DeleteConfirmationDialog


