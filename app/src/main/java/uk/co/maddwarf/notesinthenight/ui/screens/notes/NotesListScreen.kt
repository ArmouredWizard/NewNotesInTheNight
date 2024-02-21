package uk.co.maddwarf.notesinthenight.ui.screens.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import uk.co.maddwarf.notesinthenight.NotesInTheNightTopAppBar
import uk.co.maddwarf.notesinthenight.R
import uk.co.maddwarf.notesinthenight.model.Note
import uk.co.maddwarf.notesinthenight.navigation.NavigationDestination
import uk.co.maddwarf.notesinthenight.ui.composables.DeleteConfirmationDialog
import uk.co.maddwarf.notesinthenight.ui.composables.InfoPopUp
import uk.co.maddwarf.notesinthenight.ui.composables.MySpinner
import uk.co.maddwarf.notesinthenight.ui.composables.NameAndDescrEntryDialog
import uk.co.maddwarf.notesinthenight.ui.composables.NoteItem

object NotesListDestination : NavigationDestination {
    override val route = "otes_list"
    override val titleRes = R.string.notes_list
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListScreen(
    navigateToHome: () -> Unit,
    onNavigateUp: () -> Unit,
    notesListViewModel: NotesListViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()

    val uiState by notesListViewModel.notesListUiState.collectAsState()

    var newNote by remember { mutableStateOf(Note()) }
    var showAddNoteDialog by remember { mutableStateOf(false) }
    fun addNoteClick() {
        showAddNoteDialog = !showAddNoteDialog
    }

    fun onNoteChange(noteName: String) {
        newNote = newNote.copy(title = noteName)
    }

    fun onDescriptionChange(noteBody: String) {
        newNote = newNote.copy(body = noteBody)
    }

    fun doNewNoteFromPair(info: Pair<String, String>) {
        showAddNoteDialog = false
        coroutineScope.launch {
            notesListViewModel.saveNote(
                Note(
                    title = info.first,
                    body = info.second
                )
            )
        }
        newNote = Note()
    }

    if (showAddNoteDialog) {
        NameAndDescrEntryDialog(
            title = "New Note",
            name = newNote.title,
            nameLabel = "Note Title",
            nameHint = "Title for this Note",
            description = newNote.body,
            descriptionLabel = "Note Body",
            descriptionHint = "The main text for this Note",
            onDismiss = { showAddNoteDialog = !showAddNoteDialog },
            onAccept = { doNewNoteFromPair(it) },
            onNameChange = { onNoteChange(it) },
            onDescriptionChange = { onDescriptionChange(it) },
        )

    }


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NotesInTheNightTopAppBar(
                title = "List of Notes",
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior,
                navigateToHome = navigateToHome
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { addNoteClick() },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
    ) { innerPadding ->
        GeneralNotesListBody(
            notesList = uiState.list,
            onItemClick = {},//todo 
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            deleteNote = {
                notesListViewModel.deleteNote(it)
            }
        )
    }//end Scaffold

}//end General N ote List Screen

@Composable
fun GeneralNotesListBody(
    notesList: List<Note>,
    onItemClick: () -> Unit,
    modifier: Modifier,
    deleteNote: (Note) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    var displayDeleteNoteDialog by remember { mutableStateOf(false) }
    var chosenNote by remember { mutableStateOf(Note()) }

    fun doRemoveNote(note: Note) {
        displayDeleteNoteDialog = false
        coroutineScope.launch {
            deleteNote(note)
        }
    }

    fun showDeleteNote(note: Note) {
        chosenNote = note
        displayDeleteNoteDialog = !displayDeleteNoteDialog
    }

    if (displayDeleteNoteDialog) {
        DeleteConfirmationDialog(
            title = "Note",
            name = chosenNote.title,
            onAccept = { doRemoveNote(chosenNote) },
            onDismiss = { displayDeleteNoteDialog = false }
        )
    }

    var showNotePopUp by remember { mutableStateOf(false) }
    var chosenNoteToShow by remember { mutableStateOf(Note()) }
    fun doNotePopUp(note: Note) {
        chosenNoteToShow = note
        showNotePopUp = !showNotePopUp
    }

    if (showNotePopUp) {
        InfoPopUp(
            title = "Note",
            firstTextTitle = "Note Name",
            firstText = chosenNoteToShow.title,
            secondTextTitle = "Note Description",
            secondText = chosenNoteToShow.body,
            onDismiss = { showNotePopUp = !showNotePopUp }
        )
    }

    val unfilteredTitle = stringResource(R.string.all__note_categories)
    var chosenCategory by remember { mutableStateOf(unfilteredTitle) }
    val categoryList = mutableListOf<String>()
    notesList.forEach { crew ->
        if (crew.category != "") {
            categoryList.add(crew.category)
        }
    }
    categoryList.add(unfilteredTitle)

    var categoryFilterExpanded by remember { mutableStateOf(false) }

    fun noteCategoryChooser(category: String) {
        categoryFilterExpanded = false
        chosenCategory = category
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .paint(painterResource(id = R.drawable.cobbles), contentScale = ContentScale.FillBounds)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                MySpinner(
                    expanded = categoryFilterExpanded,
                    onClick = { categoryFilterExpanded = !categoryFilterExpanded },
                    list = categoryList,
                    chooser = ::noteCategoryChooser,
                    report = chosenCategory
                )
            }

            val filteredNoteList = if (chosenCategory == unfilteredTitle) {
                notesList
            } else {
                notesList.filter { it.category == chosenCategory }.distinct().toMutableList()
            }

            if (notesList.isEmpty()) {
                Text(text = "No Notes")
            } else {
//FILTERS GO HERE

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(items = filteredNoteList, key = { it.noteId }) { item ->
                        NoteItem(
                            note = item,
                            modifier = Modifier
                                .padding(6.dp),
                            displayDeleteNoteDialog = { showDeleteNote(item) },
                            onClick = { doNotePopUp(item) }
                        )
                    }
                }
            }//end IF List
        }//end column
    }//end Box


}//end Notes List Body


