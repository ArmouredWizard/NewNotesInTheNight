package uk.co.maddwarf.notesinthenight.ui.screens.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import uk.co.maddwarf.notesinthenight.NotesInTheNightTopAppBar
import uk.co.maddwarf.notesinthenight.R
import uk.co.maddwarf.notesinthenight.model.Crew
import uk.co.maddwarf.notesinthenight.model.Note
import uk.co.maddwarf.notesinthenight.model.Scoundrel
import uk.co.maddwarf.notesinthenight.model.Tag
import uk.co.maddwarf.notesinthenight.navigation.NavigationDestination

object NoteEntryDestination : NavigationDestination {
    override val route = "note_entry"
    override val titleRes = R.string.note_entry
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEntryScreen(
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: NoteEntryViewModel = hiltViewModel()

) {

    val coroutineScope = rememberCoroutineScope()

    val tagsList by viewModel.getNotesTags.collectAsState(initial = listOf())
    val everyScoundrelList by viewModel.getScoundrelList.collectAsState(initial = listOf())
    val everyCrewList by viewModel.getCrewList.collectAsState(initial = listOf())

    Scaffold(
        topBar = {
            NotesInTheNightTopAppBar(
                title = "Enter Note",
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp,
                navigateToHome = navigateToHome
            )
        }
    ) { innerPadding ->
        NoteEntryBody(
            noteUiState = viewModel.noteEntryUiState,
            onItemValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveNote()
                    navigateBack()
                }
            },
            tagsList = tagsList,
            everyScoundrelList = everyScoundrelList,
            everyCrewList = everyCrewList,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),

            )
    }//end scaffold

}//end Note Entry Screen

@Composable
fun NoteEntryBody(
    noteUiState: NoteEntryUiState,
    onItemValueChange: (Note) -> Unit,
    onSaveClick: () -> Unit,
    tagsList: List<Tag>,
    everyScoundrelList: List<Scoundrel>,
    everyCrewList: List<Crew>,
    modifier: Modifier
) {

    var newTag by remember { mutableStateOf("") }

    fun onNewTagChange(noteCategory: String) {
        newTag = noteCategory
    }

    fun onValueChange(note: Note) {
        onItemValueChange(note)
        newTag = ""
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .paint(
                painterResource(id = R.drawable.cobbles),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .padding(10.dp)
        ) {

            NoteInputForm(
                noteDetails = noteUiState.noteDetails,
                onValueChange = { onValueChange(it) },
                newTag = newTag,
                tagsList = tagsList,
                everyScoundrelList = everyScoundrelList,
                everyCrewList = everyCrewList,
                onNewTagChange = { onNewTagChange(it) }
            )
            Button(
                onClick = onSaveClick,
                enabled = noteUiState.isEntryValid,
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Save",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.LightGray
                )
            }

        }//end column
    }//end box
}//end Note Entry body

