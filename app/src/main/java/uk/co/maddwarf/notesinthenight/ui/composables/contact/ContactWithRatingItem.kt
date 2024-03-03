package uk.co.maddwarf.notesinthenight.ui.composables.contact

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import uk.co.maddwarf.notesinthenight.model.ContactWithRating

@Composable
fun ContactWithRatingItem(
    contact: ContactWithRating,
    modifier: Modifier = Modifier,
    enableDelete: Boolean = true,
    displayDeleteContactDialog: (ContactWithRating) -> Unit,
    onRatingClick: (Int) -> Unit,
    onClick: (ContactWithRating) -> Unit,
    changeRating: Boolean = true
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
                    .clickable { onClick(contact) }
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = contact.contactName,
                    style = MaterialTheme.typography.bodyMedium
                )

                var ratingImage = Icons.Default.Favorite
                var ratingComment = "Unrated"
                var ratingTint = Color.Cyan

                when (contact.rating) {
                    1 -> {
                        ratingImage = Icons.Default.Favorite
                        ratingComment = "Like"
                        ratingTint = Color.Green
                    }

                    0 -> {
                        ratingImage = Icons.Default.Menu
                        ratingComment = "Neutral"
                        ratingTint = Color.Gray
                    }

                    -1 -> {
                        ratingImage = Icons.Default.Warning
                        ratingComment = "Dislike"
                        ratingTint = Color.Red
                    }
                }
                RatingIcon(
                    imageVector = ratingImage,
                    contentDescription = ratingComment,
                    tint = ratingTint,
                    changeRating = changeRating,
                    onRatingClick = onRatingClick,
                )

                if (enableDelete) {
                    Icon(
                        imageVector = Icons.TwoTone.Delete,
                        contentDescription = "Delete",
                        Modifier
                            .clickable { displayDeleteContactDialog(contact) }
                            .align(Alignment.CenterVertically)
                            .size(25.dp)
                    )
                }
            }
        }
    }
}//end ContactItem

@Composable
fun RatingIcon(
    imageVector: ImageVector,
    contentDescription: String,
    tint: Color,
    onRatingClick: (Int) -> Unit,
    changeRating: Boolean
) {
    Box(
        modifier = Modifier.wrapContentSize()
    ) {
        var showRatingMenu by remember { mutableStateOf(false) }

        fun ratingChooser(rating: Int) {
            showRatingMenu = false
            onRatingClick(rating)
        }

        if (showRatingMenu && changeRating) {
            MyRatingSpinner(
                list = listOf("-1", "0", "1"),
                chooser = ::ratingChooser,
                onClick = { showRatingMenu = !showRatingMenu }
            )
        }

        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.clickable { showRatingMenu = !showRatingMenu }
        )
    }
}

@Composable
fun MyRatingSpinner(
    list: List<String>,
    chooser: (Int) -> Unit,
    onClick: () -> Unit
) {
//todo
    DropdownMenu(expanded = true, onDismissRequest = { onClick() }) {
        list.forEach {
            @Composable
            fun menuIcon() = when (it) {
                "-1" -> Icon(Icons.Default.Warning, contentDescription = "bad", tint = Color.Red)
                "0" -> Icon(Icons.Default.Menu, contentDescription = "bad", tint = Color.Gray)
                "1" -> Icon(Icons.Default.Favorite, contentDescription = "bad", tint = Color.Green)
                else -> {}
            }
            DropdownMenuItem(
                text = { menuIcon() },
                onClick = { chooser(it.toInt()); Log.d("RATING CLICK", it) })
        }
    }
}