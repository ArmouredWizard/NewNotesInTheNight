package uk.co.maddwarf.notesinthenight.ui.screens.notes

import android.util.Log
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
import kotlinx.coroutines.newFixedThreadPoolContext
import uk.co.maddwarf.notesinthenight.NotesInTheNightTopAppBar
import uk.co.maddwarf.notesinthenight.R
import uk.co.maddwarf.notesinthenight.model.Note
import uk.co.maddwarf.notesinthenight.model.Tag
import uk.co.maddwarf.notesinthenight.navigation.NavigationDestination
import uk.co.maddwarf.notesinthenight.ui.composables.DeleteConfirmationDialog
import uk.co.maddwarf.notesinthenight.ui.composables.InfoPopUp
import uk.co.maddwarf.notesinthenight.ui.composables.MySpinner
import uk.co.maddwarf.notesinthenight.ui.composables.note.NoteItem
import uk.co.maddwarf.notesinthenight.ui.composables.note.NoteEntryDialog

object NotesListDestination : NavigationDestination {
    override val route = "notes_list"
    override val titleRes = R.string.notes_list
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListScreen(
    navigateToHome: () -> Unit,
    onNavigateUp: () -> Unit,
    notesListViewModel: NotesListViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    val uiState by notesListViewModel.notesListUiState.collectAsState()

    val tagsList by notesListViewModel.getNotesTags.collectAsState(initial = listOf())

    var displayEditNoteDialog by remember { mutableStateOf(false) }

    var newNote by remember { mutableStateOf(Note()) }
    var showAddNoteDialog by remember { mutableStateOf(false) }
    fun addNoteClick() {
        showAddNoteDialog = !showAddNoteDialog
    }

    fun onNoteTitleChange(noteName: String) {
        newNote = newNote.copy(title = noteName)
    }

    fun onNoteBodyChange(noteBody: String) {
        newNote = newNote.copy(body = noteBody)
    }

    var newtag by remember{ mutableStateOf("") }

    fun onNoteCategoryChange(noteCategory: String) {
        newtag = noteCategory
    }

    fun showEditNote(note: Note) {
        newNote = note
        newtag = ""
        displayEditNoteDialog = !displayEditNoteDialog
    }

    fun doNewNote(note: Note) {
        Log.d("NEW NOTE", note.toString())
        showAddNoteDialog = false
        coroutineScope.launch {
            notesListViewModel.saveNote(note)
        }
        newNote = Note()
        newtag = ""
    }

    fun doEditNote(note: Note) {
        Log.d("EDIT NOTE", note.toString())
        displayEditNoteDialog = false
        coroutineScope.launch {
            notesListViewModel.saveEditedNote(note)
        }
        newNote = Note()
    }

    fun onTagAdd(tag:String){
        //newNote.tags.add(Tag(tag = tag))
        newNote = newNote.copy(tags = (newNote.tags+Tag(tag = tag)).toMutableList())
        newtag = ""
    }

    if (displayEditNoteDialog) {
        NoteEntryDialog(
            noteId = newNote.noteId,
            title = newNote.title,
            body = newNote.body,
            tags = newNote.tags,
            newTag = newtag,
            onDismiss = {
                displayEditNoteDialog = false
                newNote = Note()
            },
            onAccept = { doEditNote(it) },
            onTitleChange = { onNoteTitleChange(it) },
            onBodyChange = { onNoteBodyChange(it) },
            onCategoryChange = { onNoteCategoryChange(it) },
            tagsList = tagsList,
            onTagAdd = {onTagAdd(it)}
        )
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
        NoteEntryDialog(
            noteId = 0,
            title = newNote.title,
            body = newNote.body,
            tags = newNote.tags,
            newTag = newtag,
            onDismiss = {
                showAddNoteDialog = false
                newNote = Note()
                        },
            onAccept = { doNewNote(it) },
            onTitleChange = { onNoteTitleChange(it) },
            onBodyChange = { onNoteBodyChange(it) },
            onCategoryChange = { onNoteCategoryChange(it) },
            tagsList = tagsList,
            onTagAdd = { onTagAdd(it) }
        )
    }



    Scaffold(
        modifier = Modifier,
        topBar = {
            NotesInTheNightTopAppBar(
                title = "List of Notes",
                canNavigateBack = true,
                navigateUp = onNavigateUp,
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
            tagsList = tagsList,
            onItemClick = {},//todo 
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            deleteNote = {
                notesListViewModel.deleteNote(it)
            },
            showEditNote = { showEditNote(it) }
        )
    }//end Scaffold

}//end General Note List Screen

@Composable
fun GeneralNotesListBody(
    notesList: List<Note>,
    onItemClick: () -> Unit,
    modifier: Modifier,
    deleteNote: (Note) -> Unit,
    tagsList: List<Tag>,
    showEditNote: (Note) -> Unit
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
        Log.d("NOTE", note.toString())
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

    val unfilteredTitle = stringResource(R.string.all_note_categories)
    var chosenCategory by remember { mutableStateOf(unfilteredTitle) }
    val categoryList: MutableList<Tag> = mutableListOf(Tag(tag = unfilteredTitle))
    categoryList.addAll(tagsList)

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                MySpinner(
                    expanded = categoryFilterExpanded,
                    onClick = { categoryFilterExpanded = !categoryFilterExpanded },
                    list = categoryList.map {
                        it.tag
                    }.distinct(),
                    chooser = ::noteCategoryChooser,
                    report = chosenCategory
                )
            }


            var filteredNoteList: MutableList<Note> = mutableListOf()

            if (chosenCategory == unfilteredTitle) {
                filteredNoteList = notesList.toMutableList()
            } else {
                // notesList.filter { it.tags.contains(chosenCategory) }.distinct().toMutableList()

                notesList.forEach { note ->
                    note.tags.forEach { tag ->
                        if (tag.tag == chosenCategory) {
                            filteredNoteList.add(note)
                        }
                    }
                }
                filteredNoteList.distinct()
            }

            if (notesList.isEmpty()) {
                Text(text = "No Notes")
            } else {
//FILTERS GO HERE

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(items = filteredNoteList/*, key = { it.noteId }*/) { item ->
                        NoteItem(
                            note = item,
                            modifier = Modifier
                                .padding(6.dp),
                            displayDeleteNoteDialog = { showDeleteNote(item) },
                            displayEditNoteDialog = { showEditNote(item) },
                            onClick = { doNotePopUp(item) }
                        )
                    }
                }
            }//end IF List
        }//end column
    }//end Box


}//end Notes List Body


