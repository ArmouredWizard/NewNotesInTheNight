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
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import uk.co.maddwarf.notesinthenight.ui.composables.DeleteConfirmationDialog
import uk.co.maddwarf.notesinthenight.ui.composables.MySpinner
import uk.co.maddwarf.notesinthenight.ui.composables.note.NoteItem
import uk.co.maddwarf.notesinthenight.ui.composables.note.NoteEntryDialog
import uk.co.maddwarf.notesinthenight.ui.composables.note.NoteInfoPopUp

object NotesListDestination : NavigationDestination {
    override val route = "notes_list"
    override val titleRes = R.string.notes_list
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListScreen(
    navigateToHome: () -> Unit,
    navigateToAddNoteScreen: () -> Unit,
    navigateToNoteEdit: (Int) -> Unit,
    onNavigateUp: () -> Unit,
    notesListViewModel: NotesListViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    val uiState by notesListViewModel.notesListUiState.collectAsState()

    val tagsList by notesListViewModel.getNotesTags.collectAsState(initial = listOf())
    val everyScoundrelList by notesListViewModel.getAllScoundrels.collectAsState(listOf())
    val everyCrewList by notesListViewModel.getAllCrews.collectAsState(listOf())

    var displayEditNoteDialog by remember { mutableStateOf(false) }

    var newNote by remember { mutableStateOf(Note()) }
    var showAddNoteDialog by remember { mutableStateOf(false) }
    fun addNoteClick() {
        //  showAddNoteDialog = !showAddNoteDialog
        navigateToAddNoteScreen()
    }

   /* fun onNoteTitleChange(noteName: String) {
        newNote = newNote.copy(title = noteName)
    }*/

  /*  fun onNoteBodyChange(noteBody: String) {
        newNote = newNote.copy(body = noteBody)
    }*/

    var newTag by remember { mutableStateOf("") }

   /* fun onNoteCategoryChange(noteCategory: String) {
        newTag = noteCategory
    }*/

    fun editNoteClick(note: Note) {
        newNote = note
        newTag = ""
        navigateToNoteEdit(note.noteId)

        //displayEditNoteDialog = !displayEditNoteDialog
    }

   /* fun doNewNote(note: Note) {
        Log.d("NEW NOTE", note.toString())
        showAddNoteDialog = false
        coroutineScope.launch {
            notesListViewModel.saveNote(note)
        }
        newNote = Note()
        newTag = ""
    }*/

   /* fun doEditNote(note: Note) {
        Log.d("EDIT NOTE", note.toString())
        displayEditNoteDialog = false
        coroutineScope.launch {
            notesListViewModel.saveEditedNote(note)
        }
        newNote = Note()
    }*/

   /* fun onTagAdd(tag: Tag) {
        newNote = newNote.copy(tags = (newNote.tags + tag).toMutableList())
        newTag = ""
    }*/

   /* fun onScoundrelAdd(scoundrel: Scoundrel) {
        newNote = newNote.copy(scoundrels = (newNote.scoundrels + scoundrel).toMutableList())
    }*/

    /* fun onCrewAdd(crew: Crew) {
         Log.d("CREW ON ADD", newNote.toString())
         newNote = newNote.copy(crews = (newNote.crews + crew).toMutableList())
         Log.d("CREW After ADD", newNote.toString())
     }*/

  /*  if (displayEditNoteDialog) {
        NoteEntryDialog(
            noteId = newNote.noteId,
            title = newNote.title,
            body = newNote.body,
            tags = newNote.tags,
            newTag = newTag,

            scoundrels = newNote.scoundrels,
            crews = newNote.crews,

            onDismiss = {
                displayEditNoteDialog = false
                newNote = Note()
            },
            onAccept = { doEditNote(it) },
            onTitleChange = { onNoteTitleChange(it) },
            onBodyChange = { onNoteBodyChange(it) },
            onCategoryChange = { onNoteCategoryChange(it) },
            tagsList = tagsList,
            onTagAdd = { onTagAdd(it) },
            everyScoundrelList = everyScoundrelList,
            onScoundrelAdd = { onScoundrelAdd(it) },
            everyCrewList = everyCrewList,
            onCrewAdd = { onCrewAdd(it) }
        )
    }*/

    /*  if (showAddNoteDialog) {
          NoteEntryDialog(
              noteId = 0,
              title = newNote.title,
              body = newNote.body,
              tags = newNote.tags,
              newTag = newTag,

              scoundrels = newNote.scoundrels,
              crews = newNote.crews,

              onDismiss = {
                  showAddNoteDialog = false
                  newNote = Note()
              },
              onAccept = { doNewNote(it) },
              onTitleChange = { onNoteTitleChange(it) },
              onBodyChange = { onNoteBodyChange(it) },
              onCategoryChange = { onNoteCategoryChange(it) },
              tagsList = tagsList,
              onTagAdd = { onTagAdd(it) },
              everyScoundrelList = everyScoundrelList,
              onScoundrelAdd = { onScoundrelAdd(it) },
              everyCrewList = everyCrewList,
              onCrewAdd = { onCrewAdd(it) }
          )
      }*/

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
        NotesListBody(
            notesList = uiState.list,
            tagsList = tagsList,
            everyScoundrelList = everyScoundrelList,
            everyCrewList = everyCrewList,
            onItemClick = {},
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            deleteNote = {
                notesListViewModel.deleteNote(it)
            },
            showEditNote = { editNoteClick(it) }
        )
    }//end Scaffold
}//end General Note List Screen

@Composable
fun NotesListBody(
    notesList: List<Note>,
    onItemClick: () -> Unit,
    modifier: Modifier,
    deleteNote: (Note) -> Unit,
    tagsList: List<Tag>,
    everyScoundrelList: List<Scoundrel>,
    everyCrewList: List<Crew>,
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
        NoteInfoPopUp(
            title = chosenNoteToShow.title,
            firstTextTitle = "Note Body",
            firstText = chosenNoteToShow.body,
            secondTextTitle = "Note Tags",
            secondText = chosenNoteToShow.tags.map {
                it.tag
            }.toString(), //todo format properly
            thirdTitle = "Scoundrels",
            thirdText = chosenNoteToShow.scoundrels.map {
                it.name
            }.toString(),
            fourthTitle = "Crews",
            fourthText = chosenNoteToShow.crews.map {
                it.crewName
            }.toString(),
            onDismiss = { showNotePopUp = !showNotePopUp }
        )
    }

    val unfilteredTagTitle = stringResource(R.string.all_note_tags)
    var chosenCategory by remember { mutableStateOf(unfilteredTagTitle) }
    val categoryList: MutableList<Tag> = mutableListOf(Tag(tag = unfilteredTagTitle))
    categoryList.addAll(tagsList)

    var categoryFilterExpanded by remember { mutableStateOf(false) }

    fun noteCategoryChooser(category: String) {
        categoryFilterExpanded = false
        chosenCategory = category
    }

    val unfilteredScoundrelTitle = stringResource(R.string.all_note_scoundrels)
    var chosenScoundrel by remember { mutableStateOf(unfilteredScoundrelTitle) }
    val scoundrelList: MutableList<Scoundrel> =
        mutableListOf(Scoundrel(name = unfilteredScoundrelTitle))
    scoundrelList.addAll(everyScoundrelList)

    var scoundrelFilterExpanded by remember { mutableStateOf(false) }

    fun noteScoundrelChooser(scoundrel: String) {
        scoundrelFilterExpanded = false
        chosenScoundrel = scoundrel
    }

    val unfilteredCrewTitle = stringResource(R.string.all_note_crews)
    var chosenCrew by remember { mutableStateOf(unfilteredCrewTitle) }
    val crewList: MutableList<Crew> =
        mutableListOf(Crew(crewName = unfilteredCrewTitle))
    crewList.addAll(everyCrewList)

    var crewFilterExpanded by remember { mutableStateOf(false) }

    fun noteCrewChooser(crew: String) {
        crewFilterExpanded = false
        chosenCrew = crew
    }

    var tagFilteredNoteList: MutableList<Note> = mutableListOf()
    var scoundrelFilteredNoteList: MutableList<Note> = mutableListOf()
    var crewFilteredNoteList: MutableList<Note> = mutableListOf()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .paint(
                painterResource(id = R.drawable.cobbles),
                contentScale = ContentScale.FillBounds
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                Row(modifier = Modifier.fillMaxWidth(.3f)) {
                    MySpinner(
                        expanded = categoryFilterExpanded,
                        onClick = { categoryFilterExpanded = !categoryFilterExpanded },
                        list = categoryList.map {
                            it.tag
                        }.distinct(),
                        chooser = ::noteCategoryChooser,
                        report = chosenCategory
                    )

                    if (chosenCategory == unfilteredTagTitle) {
                        tagFilteredNoteList = notesList.toMutableList()
                    } else {
                        notesList.forEach { note ->
                            note.tags.forEach { tag ->
                                if (tag.tag == chosenCategory) {
                                    tagFilteredNoteList.add(note)
                                }
                            }
                        }
                        tagFilteredNoteList.distinct()
                    }
                }//end Tag Filter Row

                Row(modifier = Modifier.fillMaxWidth(.5f)) {
                    MySpinner(
                        expanded = scoundrelFilterExpanded,
                        onClick = { scoundrelFilterExpanded = !scoundrelFilterExpanded },
                        list = scoundrelList.map {
                            it.name
                        }.distinct(),
                        chooser = ::noteScoundrelChooser,
                        report = chosenScoundrel
                    )

                    // var filteredNoteList: MutableList<Note> = mutableListOf()
                    if (chosenScoundrel == unfilteredScoundrelTitle) {
                        scoundrelFilteredNoteList = tagFilteredNoteList.toMutableList()
                    } else {
                        Log.d("SCOUNDREL", chosenScoundrel)
                        tagFilteredNoteList.forEach { note ->
                            note.scoundrels.forEach { scoundrel ->
                                if (scoundrel.name == chosenScoundrel) {
                                    scoundrelFilteredNoteList.add(note)
                                }
                            }
                        }
                        tagFilteredNoteList.distinct()
                    }
                }//end Scoundrel Filter Row

                Row(modifier = Modifier.fillMaxWidth()) {
                    MySpinner(
                        expanded = crewFilterExpanded,
                        onClick = { crewFilterExpanded = !crewFilterExpanded },
                        list = crewList.map {
                            it.crewName
                        }.distinct(),
                        chooser = ::noteCrewChooser,
                        report = chosenCrew
                    )

                    // var filteredNoteList: MutableList<Note> = mutableListOf()
                    if (chosenCrew == unfilteredCrewTitle) {
                        crewFilteredNoteList = scoundrelFilteredNoteList.toMutableList()
                    } else {
                        scoundrelFilteredNoteList.forEach { note ->
                            Log.d("CHECKING NOTE", note.title)
                            note.crews.forEach { crew ->
                                if (crew.crewName == chosenCrew) {
                                    crewFilteredNoteList.add(note)
                                }
                            }
                        }
                        crewFilteredNoteList.distinct()
                    }
                }//end Crew Filter Row

            }//end Filter Row

            if (crewFilteredNoteList.isEmpty()) {
                Text(text = "No Notes")
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    items(items = crewFilteredNoteList) { item ->
                        NoteItem(
                            note = item,
                            modifier = Modifier
                                .padding(6.dp),
                            displayDeleteNoteDialog = { showDeleteNote(item) },
                            displayEditNoteDialog = { showEditNote(item) },
                            onClick = { doNotePopUp(item) }
                        )
                    }

                }//end LazyColumn
            }//end IF List

        }//end column
    }//end Box

}//end Notes List Body


