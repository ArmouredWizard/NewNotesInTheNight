package uk.co.maddwarf.notesinthenight.ui.screens.scoundrel

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
class ScoundrelDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val scoundrelUseCase: ScoundrelUseCase,
): ViewModel(){
    private val scoundrelId: Int = checkNotNull(savedStateHandle[ScoundrelDetailsDestination.itemIdArg])

    val detailsUiState: StateFlow<ScoundrelDetailsUiState> =
        scoundrelUseCase.getFullScoundrelData(scoundrelId)
            .filterNotNull()
            .map { ScoundrelDetailsUiState(scoundrelDetails = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ScoundrelDetailsUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}//end View Model

data class ScoundrelDetailsUiState(
    val scoundrelDetails: Scoundrel = Scoundrel()
)