package uk.co.maddwarf.notesinthenight.ui.screens.scoundrel

import android.util.Log
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
import uk.co.maddwarf.notesinthenight.model.Scoundrel
import javax.inject.Inject

@HiltViewModel
class ScoundrelEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val scoundrelUseCase: ScoundrelUseCase,
) :
    ViewModel() {

    private val scoundrelId: Int =
        checkNotNull(savedStateHandle[ScoundrelEditDestination.itemIdArg])

    var editUiState: StateFlow<ScoundrelEditUiState> =
        scoundrelUseCase.getFullScoundrelData(scoundrelId)
            .filterNotNull()
            .map {
                ScoundrelEditUiState(scoundrelDetails = it)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ScoundrelEditUiState()
            )

    var intermediateScoundrelUiState by mutableStateOf(IntermediateScoundrelUiState())
        private set

    var scoundrelList = scoundrelUseCase.getListOfScoundrels()

    var abilityList = scoundrelUseCase.getListOfAbilities()
    val contactList = scoundrelUseCase.getListOfContacts()
    val crewList = scoundrelUseCase.getListOfCrews()

    fun updateIntermediateUiState(scoundrelDetails: Scoundrel) {
        Log.d("UPADATE SCOUNDREL EDIT", scoundrelDetails.contacts.toString())
        intermediateScoundrelUiState = IntermediateScoundrelUiState(
            intermediateScoundrelDetails = scoundrelDetails,
            isEntryValid = validateInput(scoundrelDetails)
        )
    }

    fun initialise() {
        updateIntermediateUiState(editUiState.value.scoundrelDetails)
    }

    private fun validateInput(scoundrel: Scoundrel = intermediateScoundrelUiState.intermediateScoundrelDetails): Boolean {
        return with(scoundrel) {
            name.isNotBlank() //&& playbook.isNotBlank() //&& crew.isNotBlank()
        }
    }

    suspend fun saveItem() {
        if (validateInput()) {
            //TODO
            scoundrelUseCase.saveEditedScoundrel(intermediateScoundrelUiState.intermediateScoundrelDetails)
        }//end valid
    }//end save


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}//end ViewModel

data class ScoundrelEditUiState(
    var scoundrelDetails: Scoundrel = Scoundrel()
)

data class IntermediateScoundrelUiState(
    var intermediateScoundrelDetails: Scoundrel = Scoundrel(),
    var isEntryValid: Boolean = false
)