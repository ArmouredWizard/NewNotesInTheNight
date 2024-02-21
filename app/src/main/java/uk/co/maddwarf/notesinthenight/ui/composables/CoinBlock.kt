package uk.co.maddwarf.notesinthenight.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.co.maddwarf.notesinthenight.model.Scoundrel

@Composable
fun CoinBlock(
    scoundrelDetails: Scoundrel,
    onRankClicked: (String, Int) -> Unit = { s: String, i: Int -> },
) {
    val action = "Coin"
    Card(
        modifier = Modifier.fillMaxWidth(.95f),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .weight(0.15f)
            ) {
                Text(text = "$action:")
            }
            Row(
                modifier = Modifier
                    .weight(0.85f),
                horizontalArrangement = Arrangement.Center
            ) {
                for (c in 1..8) {
                    ActionIcon(
                        action = action,
                        index = c,
                        onRankClicked = onRankClicked,
                        rank = scoundrelDetails.coin
                    )
                }
            }
        }//end Main Row
    }//end Card
}//end coin block