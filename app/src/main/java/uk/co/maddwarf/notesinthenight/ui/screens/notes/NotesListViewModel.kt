package uk.co.maddwarf.notesinthenight.ui.screens.notes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uk.co.maddwarf.notesinthenight.domain.ScoundrelUseCase
import uk.co.maddwarf.notesinthenight.model.Note
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val scoundrelUseCase: ScoundrelUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {//end ViewModel

    val notesListUiState: StateFlow<NotesListUiState> =
        scoundrelUseCase.getAllFullNotes()
            .map { NotesListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = NotesListUiState()
            )

    fun deleteNote(note: Note) =
        viewModelScope.launch {
            scoundrelUseCase.deleteNote(note)
        }

    suspend fun saveNote(note: Note) =
        scoundrelUseCase.saveNote(note)

    suspend fun saveEditedNote(note: Note) {
        scoundrelUseCase.saveEditedNote(note)
    }

    var getNotesTags = scoundrelUseCase.getNotesTags()

}

data class NotesListUiState(
    val list: List<Note> = listOf()
)