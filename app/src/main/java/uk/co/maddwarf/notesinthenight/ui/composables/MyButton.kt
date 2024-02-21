package uk.co.maddwarf.notesinthenight.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    text: String,
) {
    Button(
        modifier = modifier.fillMaxWidth(.9f),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        border = BorderStroke(width = 1.dp, color = Color.Black),

        ) {
        if (leadingIcon != null) {
            Icon(imageVector = leadingIcon, contentDescription = "Leading Icon")
        }
        Text(
            text = text,
            color = Color.LightGray
        )
        if (trailingIcon != null) {
            Icon(imageVector = trailingIcon, contentDescription = "Trailing Icon")
        }
    }
}