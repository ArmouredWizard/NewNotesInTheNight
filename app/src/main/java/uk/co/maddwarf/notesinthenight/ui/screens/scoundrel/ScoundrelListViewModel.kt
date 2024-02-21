package uk.co.maddwarf.notesinthenight.ui.screens.scoundrel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import uk.co.maddwarf.notesinthenight.domain.ScoundrelUseCase
import uk.co.maddwarf.notesinthenight.model.Scoundrel
import javax.inject.Inject

@HiltViewModel
class ScoundrelListViewModel @Inject constructor(
    private val scoundrelUseCase: ScoundrelUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val scoundrelListUiState: StateFlow<ScoundrelListUiState> =
        scoundrelUseCase.getListOfFullScoundrels()
            .map { ScoundrelListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = ScoundrelListUiState()
            )

    suspend fun deleteScoundrel(scoundrel: Scoundrel) =
        scoundrelUseCase.deleteScoundrel(scoundrel)

}//end ViewModel

data class ScoundrelListUiState(
    val list: List<Scoundrel> = listOf()
)