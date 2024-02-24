package uk.co.maddwarf.notesinthenight.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp

@Composable
fun TextEntryRowWithInfoIcon(
    data: String,
    onValueChange: (String) -> Unit,
    label: String,
    infoText: String,
    singleLine: Boolean = true,
    width: Float = 0.9f,
    limited: Boolean = false
) {
    val showDialog = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(/*width*/),
            value = data,
            onValueChange = onValueChange,
            label = { Text(text = label) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer, //todo fix these colours
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info",
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable(
                            onClick = { showDialog.value = true }
                        )
                )//end Icon
            }

        )//end OutlinedTextField

    }
    if (showDialog.value) {
        InfoDialog(
            open = showDialog.value,
            onDismiss = { showDialog.value = false },
            title = label,
            body = infoText
        )
    }
}//end TextEntryWithInfoIcon

@Composable
fun TextEntryWithSpinner(
    textValue: String,
    label: String,
    infoText: String,
    onValueChange: (String) -> Unit = {},
    itemList: List<String>,
    limited: Boolean = true
) {
    var spinnerExpanded by remember { mutableStateOf(false) }
    var chosenItem by remember {
        mutableStateOf(
            "Default"
        )
    }

    fun itemChooser(item: String) {
        spinnerExpanded = false
        chosenItem = item
        onValueChange(chosenItem)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.width(70.dp)
        ) {
            MySpinner(
                expanded = spinnerExpanded,
                onClick = { spinnerExpanded = !spinnerExpanded },
                list = itemList,
                chooser = ::itemChooser,
                report = "" //chosenItem
            )
        }
        Spacer(modifier = Modifier.width(5.dp))
        Row(
            modifier = Modifier.weight(1f)
        ) {
            TextEntryRowWithInfoIcon(
                data = textValue,
                onValueChange = {
                    //  if (it.length <= 19 || !limited) {
                    onValueChange(it)
                    //  }
                },
                label = label,
                infoText = infoText,
                width = 0.9f,
                limited = limited
            )
        }
    }
}//end TextEntryWithSpinner
