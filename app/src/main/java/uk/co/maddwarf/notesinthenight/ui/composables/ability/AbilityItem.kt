package uk.co.maddwarf.notesinthenight.ui.composables.ability

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.co.maddwarf.notesinthenight.model.SpecialAbility

@Composable
fun AbilityItem(
    ability: SpecialAbility,
    modifier: Modifier = Modifier,
    enableDelete: Boolean = true,
    displayDeleteAbilityDialog: (SpecialAbility) -> Unit,
    onClick: (SpecialAbility) -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(.9f),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .clickable { onClick(ability) }
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = ability.abilityName,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (enableDelete) {
                    Icon(
                        imageVector = Icons.TwoTone.Delete,
                        contentDescription = "Delete",
                        Modifier
                            .clickable { displayDeleteAbilityDialog(ability) }
                            .align(Alignment.CenterVertically)
                            .size(25.dp)
                    )
                }
            }
        }
    }
}//end AbilityItem