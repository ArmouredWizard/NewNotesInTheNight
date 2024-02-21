package uk.co.maddwarf.notesinthenight.ui.composables.scoundrel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.sp
import uk.co.maddwarf.notesinthenight.model.Scoundrel

@Composable
fun ScoundrelItem(
    scoundrel: Scoundrel,
    modifier: Modifier = Modifier,
    enableDelete: Boolean = true,
    displayDeleteScoundrelDialog: (Scoundrel) -> Unit,
    onClick: (Scoundrel) -> Unit,
    showCrew: Boolean = true
) {
    val crewName = if (scoundrel.crew == null) {
        "No Crew"
    } else {
        scoundrel.crew!!.crewName
    }

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
                    .clickable { onClick(scoundrel) }
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .weight(.8f)
                ) {
                    Text(
                        text = scoundrel.name,
                        fontSize = 20.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        if (showCrew) {
                            Text(
                                text = "Crew: $crewName", modifier = Modifier.weight(.49f),
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                        }
                        Text(
                            text = "Playbook: ${scoundrel.playbook}",
                            modifier = Modifier.weight(.4f),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }//end column
                if (enableDelete) {
                    Row(
                        modifier = Modifier
                            .weight(.12f),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.TwoTone.Delete,
                            contentDescription = "Delete",
                            Modifier
                                .clickable { displayDeleteScoundrelDialog(scoundrel) }
                                .size(25.dp)
                        )
                    }//end Icon Row
                }//end if
            }//end main row
        }//end main column
    }//end card
}//end ContactItem