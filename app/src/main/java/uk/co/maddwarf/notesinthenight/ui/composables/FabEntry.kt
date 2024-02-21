package uk.co.maddwarf.notesinthenight.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun FabEntry(
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    title: String = "",
    onClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(5.dp))
    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = Color.DarkGray)
            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(20.dp))
            .padding(10.dp)
            .clickable { onClick() }

    ) {
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = "Leading Icon",
                tint = Color.LightGray
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.LightGray
        )
    }
}