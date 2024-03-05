package uk.co.maddwarf.notesinthenight.ui.screens.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import uk.co.maddwarf.notesinthenight.domain.ScoundrelUseCase
import uk.co.maddwarf.notesinthenight.model.Note
import javax.inject.Inject

@HiltViewModel
class NoteEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val scoundrelUseCase: ScoundrelUseCase,
) :
    ViewModel() {

    private val noteId: Int =
        checkNotNull(savedStateHandle[NoteEditDestination.itemIdArg])

    var editUiState: StateFlow<NoteEditUiState> =
        scoundrelUseCase.getFullNoteById(noteId)
            .filterNotNull()
            .map {
                NoteEditUiState(noteDetails = it)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = NoteEditUiState()
            )

    var intermediateNoteUiState by mutableStateOf(IntermediateNoteUiState())
        private set

    var getNotesTags = scoundrelUseCase.getNotesTags()
    var scoundrelList = scoundrelUseCase.getListOfScoundrels()
    val crewList = scoundrelUseCase.getListOfCrews()

    fun updateIntermediateUiState(noteDetails: Note) {
        intermediateNoteUiState = IntermediateNoteUiState(
            intermediateNoteDetails = noteDetails,
            isEntryValid = validateInput(noteDetails)
        )
    }

    fun initialise() {
        updateIntermediateUiState(editUiState.value.noteDetails)
    }

    private fun validateInput(note: Note = intermediateNoteUiState.intermediateNoteDetails): Boolean {
        return with(note) {
            title.isNotBlank() //&& playbook.isNotBlank() //&& crew.isNotBlank()
        }
    }

    suspend fun saveItem() {
        if (validateInput()) {
            //TODO
            scoundrelUseCase.saveEditedNote(intermediateNoteUiState.intermediateNoteDetails)
        }//end valid
    }//end save


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}//end Note Edit view Model

data class NoteEditUiState(
    var noteDetails: Note = Note()
)

data class IntermediateNoteUiState(
    var intermediateNoteDetails: Note = Note(),
    var isEntryValid: Boolean = false
)