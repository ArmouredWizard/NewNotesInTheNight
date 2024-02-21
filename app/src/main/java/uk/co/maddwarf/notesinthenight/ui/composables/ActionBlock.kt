package uk.co.maddwarf.notesinthenight.ui.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun ActionBlock(
    attribute: String,
    actions: List<Pair<String, Int>>,
    onRankClicked: (String, Int) -> Unit = { s: String, i: Int -> },
    rollable: Boolean = false
) {
    Log.d("ACTION", actions.toString())

    val attributeValue = getAttributeValue(actions)

    Text(
        text = "$attribute: ($attributeValue)",
        textDecoration = TextDecoration.Underline,
        fontWeight = FontWeight.Bold,
        color = Color.LightGray
    )
    actions.forEach { action ->
        ActionRow(
            action = action.first,
            rank = action.second,
            onRankClicked = onRankClicked,
            rollable = rollable
        )
    }
}

fun getAttributeValue(actions: List<Pair<String, Int>>): Int {
    var attr = 0
    actions.forEach {
        if (it.second > 0) attr++
    }
    return attr
}

@Composable
fun ActionRow(
    action: String,
    rank: Int,
    onRankClicked: (String, Int) -> Unit,
    rollable: Boolean
) {
    var showDiceRollerDialog by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .background(color = Color.LightGray)
           .wrapContentWidth()
           // .fillMaxWidth()
            .padding(1.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.3f),
            horizontalAlignment = Alignment.End
        ) {
            Text(text = "$action: ")
        }
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                horizontalArrangement = Arrangement.Start
            ) {
                ActionIcon(action = action, index = 1, onRankClicked = onRankClicked, rank = rank)
                HorizontalDivider(
                    color = Color.Gray, modifier = Modifier
                        .height(20.dp)
                        .width(2.dp)
                )
                ActionIcon(action = action, index = 2, onRankClicked = onRankClicked, rank = rank)
                ActionIcon(action = action, index = 3, onRankClicked = onRankClicked, rank = rank)
                ActionIcon(action = action, index = 4, onRankClicked = onRankClicked, rank = rank)
            }
        }
        /*
        if (rollable) {
            Spacer(modifier = Modifier.width(30.dp))
            Image(
                painterResource(id = R.drawable.dices),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clickable { showDiceRollerDialog = true }
            )
        }
        */
        /*   if (showDiceRollerDialog) {
               DiceRollerDialog(
                   onDismiss = { showDiceRollerDialog = false },
                   dice = rank.toInt(),
                   action = action
               )
           }*/
        //todo add dice roller dioalog
    }
}//end ActionRow

@Composable
fun ActionIcon(
    action: String,
    index: Int,
    onRankClicked: (action: String, rank: Int) -> Unit,
    rank: Int
) {
    val image: ImageVector = if (index > rank) {
        Icons.Filled.Clear
    } else {
        Icons.Filled.AddCircle
    }

    Icon(
        imageVector = image,
        contentDescription = null,
        modifier = Modifier
            .size(30.dp)
            .clickable {
                if (rank== 1 && index == 1) {
                    onRankClicked(action, 0)
                } else {
                    onRankClicked(action, index)
                }
            }
    )
}//end ActionIcon

