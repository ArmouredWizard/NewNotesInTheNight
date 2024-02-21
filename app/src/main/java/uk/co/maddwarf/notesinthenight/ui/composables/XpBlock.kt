package uk.co.maddwarf.notesinthenight.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun XpBlock(
    xpTracks: List<Pair<String, Int>>,
    onRankClicked: (String, Int) -> Unit = { s: String, i: Int -> },
    xpTotal: Int
) {
    Box(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            //.fillMaxWidth(.7f)
            .wrapContentWidth()
            .background(color = Color.DarkGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            xpTracks.forEach { action ->
                XpRow(
                    action = action.first,
                    rank = action.second,
                    onRankClicked = onRankClicked,
                    xpTotal = xpTotal
                )
            }
        }
    }
}

@Composable
fun XpRow(
    action: String,
    rank: Int,
    onRankClicked: (String, Int) -> Unit,
    xpTotal: Int
) {
    Row(
        modifier = Modifier
            .background(color = Color.LightGray)
            .fillMaxWidth()
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        // horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.3f),
            horizontalAlignment = Alignment.End
        ) {
            Text(text = "$action: ")
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                horizontalArrangement = Arrangement.Start
            ) {
                for (c in 1..xpTotal) {
                    XpIcon(action = action, index = c, onRankClicked = onRankClicked, rank = rank)
                }
            }
        }
    }
}

@Composable
fun XpIcon(
    action: String,
    index: Int,
    onRankClicked: (action: String, rank: Int) -> Unit,
    rank: Int
) {
    val image: ImageVector = if (index > rank.toInt()) {
        Icons.Filled.Clear
    } else {
        Icons.Filled.AddCircle
    }

    Icon(
        imageVector = image,
        contentDescription = null,
        modifier = Modifier
            .size(25.dp)
            .clickable {
                if (rank == 1 && index == 1) {
                    onRankClicked(action, 0)
                } else {
                    onRankClicked(action, index)
                }
            }
    )
}//end ActionIcon