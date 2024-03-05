package uk.co.maddwarf.notesinthenight.ui.screens.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uk.co.maddwarf.notesinthenight.domain.ScoundrelUseCase
import uk.co.maddwarf.notesinthenight.model.Note
import javax.inject.Inject

@HiltViewModel
class NoteEntryViewModel @Inject constructor(private val scoundrelUseCase: ScoundrelUseCase) :
    ViewModel() {

    var noteEntryUiState by mutableStateOf(NoteEntryUiState())
        private set

    suspend fun saveNote(
    ) {
        scoundrelUseCase.saveNote(
            noteEntryUiState.noteDetails.copy(
                //confirm details/changes/trims etc
            )
        )
    }

    fun updateUiState(noteDetails: Note) {
        noteEntryUiState =
            NoteEntryUiState(
                noteDetails = noteDetails,
                isEntryValid = validateInput(noteDetails)
            )
    }

    var getNotesTags = scoundrelUseCase.getNotesTags()
    var getScoundrelList = scoundrelUseCase.getListOfScoundrels()
    var getCrewList = scoundrelUseCase.getListOfCrews()

    private fun validateInput(uiState: Note = noteEntryUiState.noteDetails): Boolean {
        return with(uiState) {
            title.isNotBlank() //&& playbook.isNotBlank() //&& note.isNotBlank()
        }
    }//end validate Input

}//end Note Entry View Model

data class NoteEntryUiState(
    val noteDetails: Note = Note(),
    val isEntryValid: Boolean = false

)