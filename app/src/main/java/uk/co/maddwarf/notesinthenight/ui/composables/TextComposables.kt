package uk.co.maddwarf.notesinthenight.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleBlock(
    title: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(10.dp)
    ) {
        Text(
            text = "$title $text",
            fontSize = 20.sp,
            color = Color.LightGray
        )
    }
}//end TitleBlock

@Composable
fun BodyBlock(
    title: String = "",
    text: String,
    width: Float = 0.95f
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(width)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(color = Color.LightGray)
            .padding(10.dp)
    ) {
        Text(text = text)
    }
}//end BodyBlock

@Composable
fun TraitText(
    title: String,
    text: String,
    onClick: (String) -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.LightGray)
                .padding(10.dp)
                .clickable { onClick(text) }
        ) {
            Text(text = title, style = MaterialTheme.typography.bodySmall)
            Text(text = text)
        }
    }
}