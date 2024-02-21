package uk.co.maddwarf.notesinthenight.ui.screens.crew

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import uk.co.maddwarf.notesinthenight.domain.ScoundrelUseCase
import uk.co.maddwarf.notesinthenight.model.Crew
import javax.inject.Inject

@HiltViewModel
class CrewListViewModel @Inject constructor(
    private val scoundrelUseCase: ScoundrelUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val crewListUiState: StateFlow<CrewListUiState> =
        scoundrelUseCase.getListOfCrews()
            .map { CrewListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = CrewListUiState()
            )

    suspend fun deleteCrew(crew: Crew) =
        scoundrelUseCase.deleteCrew(crew)

}//end ViewModel

data class CrewListUiState(
    val list: List<Crew> = listOf()
)