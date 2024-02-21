package uk.co.maddwarf.notesinthenight.model

import androidx.compose.ui.graphics.vector.ImageVector

data class FabItem(
    val label: String = "",
    val leadingIcon: ImageVector? = null,
    val clickFunction: () -> Unit
)
