package uk.co.maddwarf.notesinthenight.ui.screens.scoundrel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uk.co.maddwarf.notesinthenight.domain.ScoundrelUseCase
import uk.co.maddwarf.notesinthenight.model.Scoundrel
import javax.inject.Inject

@HiltViewModel
class ScoundrelEntryViewModel @Inject constructor(private val scoundrelUseCase: ScoundrelUseCase) :
    ViewModel() {

    var scoundrelEntryUiState by mutableStateOf(ScoundrelEntryUiState())
        private set

    suspend fun saveItem(
    ) {
        scoundrelUseCase.saveScoundrel(
            scoundrelEntryUiState.scoundrelDetails.copy(
                //confirm details/changes/trims etc
            )
        )
    }//todo

    fun updateUiStateEntry(scoundrelDetails: Scoundrel) {
        scoundrelEntryUiState =
            ScoundrelEntryUiState(
                scoundrelDetails = scoundrelDetails,
                isEntryValid = validateInput(scoundrelDetails)
            )
    }

    var scoundrelList = scoundrelUseCase.getListOfScoundrels()

    var abilityList = scoundrelUseCase.getListOfAbilities()
    val contactList = scoundrelUseCase.getListOfContacts()
    var crewList = scoundrelUseCase.getListOfCrews()

    private fun validateInput(uiState: Scoundrel = scoundrelEntryUiState.scoundrelDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() //&& playbook.isNotBlank() //&& crew.isNotBlank()
        }
    }//end validate Input

}

data class ScoundrelEntryUiState(
    val scoundrelDetails: Scoundrel = Scoundrel(),
    val scoundrelList: List<Scoundrel> = listOf(),
    val isEntryValid: Boolean = false,
)