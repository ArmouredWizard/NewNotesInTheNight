package uk.co.maddwarf.notesinthenight.ui.screens.crew

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
import uk.co.maddwarf.notesinthenight.model.Crew
import javax.inject.Inject

@HiltViewModel
class CrewEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val scoundrelUseCase: ScoundrelUseCase,
) :
    ViewModel() {
    private val crewId: Int =
        checkNotNull(savedStateHandle[CrewEditDestination.itemIdArg])

    var editUiState: StateFlow<CrewEditUiState> =
        scoundrelUseCase.getFullCrewData(crewId)
            .filterNotNull()
            .map {
                CrewEditUiState(crewDetails = it)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = CrewEditUiState()
            )

    var intermediateCrewUiState by mutableStateOf(IntermediateCrewUiState())
        private set

    var crewList = scoundrelUseCase.getListOfCrews()

    var crewAbilityList = scoundrelUseCase.getListOfCrewAbilities()
    val contactList = scoundrelUseCase.getListOfContacts()
    val upgradeList = scoundrelUseCase.getListOfUpgrades()

    fun updateIntermediateUiState(crewDetails: Crew) {
        intermediateCrewUiState = IntermediateCrewUiState(
            intermediateCrewDetails = crewDetails,
            isEntryValid = validateInput(crewDetails)
        )
    }

    fun initialise() {
        updateIntermediateUiState(editUiState.value.crewDetails)
    }

    private fun validateInput(crew: Crew = intermediateCrewUiState.intermediateCrewDetails): Boolean {
        return with(crew) {
            crewName.isNotBlank() //&& playbook.isNotBlank() //&& crew.isNotBlank()
        }
    }

    suspend fun saveItem() {
        if (validateInput()) {
            //TODO
            scoundrelUseCase.saveEditedCrew(intermediateCrewUiState.intermediateCrewDetails)
        }//end valid
    }//end save

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}//end ViewModel

data class CrewEditUiState(
    var crewDetails: Crew = Crew()
)

data class IntermediateCrewUiState(
    var intermediateCrewDetails: Crew = Crew(),
    var isEntryValid: Boolean = false
)