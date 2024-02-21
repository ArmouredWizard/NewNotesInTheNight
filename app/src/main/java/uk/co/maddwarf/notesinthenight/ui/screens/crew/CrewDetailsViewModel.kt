package uk.co.maddwarf.notesinthenight.ui.screens.crew

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
class CrewDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val scoundrelUseCase: ScoundrelUseCase,
) : ViewModel() {
    private val crewId: Int = checkNotNull(savedStateHandle[CrewDetailsDestination.itemIdArg])

    var scoundrelList = scoundrelUseCase.getScoundrelsByCrewId(crewId)


    val detailsUiState: StateFlow<CrewDetailsUiState> =
        scoundrelUseCase.getFullCrewData(crewId)
            .filterNotNull()
            .map { CrewDetailsUiState(crewDetails = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = CrewDetailsUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}//end View Model

data class CrewDetailsUiState(
    val crewDetails: Crew = Crew()
)