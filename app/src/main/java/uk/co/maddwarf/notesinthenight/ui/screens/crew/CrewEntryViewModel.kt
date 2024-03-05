package uk.co.maddwarf.notesinthenight.ui.screens.crew

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uk.co.maddwarf.notesinthenight.domain.ScoundrelUseCase
import uk.co.maddwarf.notesinthenight.model.Crew
import javax.inject.Inject

@HiltViewModel
class CrewEntryViewModel @Inject constructor(private val scoundrelUseCase: ScoundrelUseCase) :
    ViewModel() {

    var crewEntryUiState by mutableStateOf(CrewEntryUiState())
        private set

    suspend fun saveFullCrew(
    ) {
        scoundrelUseCase.saveFullCrew(
            crewEntryUiState.crewDetails.copy(
                //confirm details/changes/trims etc
            )
        )
    }

    fun updateUiState(crewDetails: Crew) {
        crewEntryUiState =
            CrewEntryUiState(
                crewDetails = crewDetails,
                isEntryValid = validateInput(crewDetails)
            )
    }

    var crewList = scoundrelUseCase.getListOfCrews()

    var crewAbilityList = scoundrelUseCase.getListOfCrewAbilities()
    val contactList = scoundrelUseCase.getListOfContacts()
    val upgradeList = scoundrelUseCase.getListOfUpgrades()


    private fun validateInput(uiState: Crew = crewEntryUiState.crewDetails): Boolean {
        return with(uiState) {
            crewName.isNotBlank() //&& playbook.isNotBlank() //&& crew.isNotBlank()
        }
    }//end validate Input

}//end Crew Entry View model

data class CrewEntryUiState(
    val crewDetails: Crew = Crew(),
    val crewList: List<Crew> = listOf(),
    val isEntryValid: Boolean = false,
)
