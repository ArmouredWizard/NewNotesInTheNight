package uk.co.maddwarf.notesinthenight.ui.composables.crew

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import uk.co.maddwarf.notesinthenight.model.CrewAbility
import uk.co.maddwarf.notesinthenight.ui.composables.MyButton

@Composable
fun MyCrewAbilitySpinner(
    expanded: Boolean,
    onClick: () -> Unit,
    list: List<CrewAbility>,
    chooser: (CrewAbility) -> Unit,
    report: String
) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        MyButton(text = report, onClick = onClick, trailingIcon = Icons.Filled.ArrowDropDown)
        //DropDown
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = (onClick),
            modifier = Modifier
                .border(width = 2.dp, shape = RoundedCornerShape(5.dp), color = Color.DarkGray)
                .clip(shape = RoundedCornerShape(5.dp))
                .background(color = Color.LightGray)
        ) {
            list.forEachIndexed { itemIndex, itemValue ->
                DropdownMenuItem(
                    onClick = { chooser(itemValue) },
                    text = {
                        Text(
                            text = itemValue.crewAbilityName,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                )
            }//end list for each
        }//end dropdown menu
    }//end box
}