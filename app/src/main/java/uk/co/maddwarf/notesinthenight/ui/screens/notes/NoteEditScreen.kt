package uk.co.maddwarf.notesinthenight.ui.screens.notes

import android.util.Log
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
import uk.co.maddwarf.notesinthenight.ui.screens.scoundrel.ScoundrelEditBody
import uk.co.maddwarf.notesinthenight.ui.screens.scoundrel.ScoundrelEditViewModel

object NoteEditDestination : NavigationDestination {
    override val route = "note_edit"
    override val titleRes: Int = R.string.note_edit_title

    const val itemIdArg = "note_id"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: NoteEditViewModel = hiltViewModel()
) {
    val editUiState = viewModel.editUiState.collectAsState()
    val uIState = viewModel.intermediateNoteUiState

    val coroutineScope = rememberCoroutineScope()

    var title = editUiState.value.noteDetails.title

    val tagsList by viewModel.getNotesTags.collectAsState(initial = listOf())
    val everyCrewList by viewModel.crewList.collectAsState(listOf())
    val everyScoundrelList by viewModel.scoundrelList.collectAsState(initial = listOf())




    if (uIState.intermediateNoteDetails.noteId == 0) {
        viewModel.initialise()
        title = "New Note"
    }

    Log.d("NOTE IN EDIT", editUiState.value.noteDetails.toString())
    Log.d("NOTE IN EDIT INTER", uIState.intermediateNoteDetails.toString())

    Scaffold(
        topBar = {
            NotesInTheNightTopAppBar(
                title = title,
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                navigateToHome = navigateToHome
            )
        }
    ) { innerPadding ->
        NoteEditBody(
            noteUiState = uIState,
            onItemValueChange = viewModel::updateIntermediateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveItem()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .paint(
                    painterResource(id = R.drawable.cobbles),
                    contentScale = ContentScale.FillBounds
                ),
            tagsList = tagsList,
            everyCrewList = everyCrewList,
            everyScoundrelList = everyScoundrelList,

            )
    }//end scaffold

}//end edit note screen

@Composable
fun NoteEditBody(
    noteUiState: IntermediateNoteUiState,
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
                title = "Edit Note",
                noteDetails = noteUiState.intermediateNoteDetails,
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