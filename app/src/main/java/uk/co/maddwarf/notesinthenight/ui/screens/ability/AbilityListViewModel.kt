package uk.co.maddwarf.notesinthenight.ui.screens.ability

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import uk.co.maddwarf.notesinthenight.domain.ScoundrelUseCase
import uk.co.maddwarf.notesinthenight.model.CrewAbility
import uk.co.maddwarf.notesinthenight.model.CrewUpgrade
import uk.co.maddwarf.notesinthenight.model.SpecialAbility
import javax.inject.Inject

@HiltViewModel
class AbilityListViewModel @Inject constructor(
    private val scoundrelUseCase: ScoundrelUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    /*   val abilityListUiState: StateFlow<AbilityListUiState> =
           scoundrelUseCase.getListOfAbilities()
               .map { AbilityListUiState(it) }
               .stateIn(
                   scope = viewModelScope,
                   started = SharingStarted.WhileSubscribed(5000L),
                   initialValue = AbilityListUiState()
               )*/

    var abilityList = scoundrelUseCase.getListOfAbilities()
    var crewAbilityList = scoundrelUseCase.getListOfCrewAbilities()
    var crewUpgradeList = scoundrelUseCase.getListOfUpgrades()

    suspend fun saveAbility(ability: SpecialAbility) =
        scoundrelUseCase.saveAbility(ability)

    suspend fun deleteAbility(ability: SpecialAbility) =
        scoundrelUseCase.deleteAbility(ability)

    suspend fun saveCrewAbility(crewAbility: CrewAbility) =
        scoundrelUseCase.saveCrewAbility(crewAbility)

    suspend fun deleteCrewAbility(crewAbility: CrewAbility) =
        scoundrelUseCase.deleteCrewAbility(crewAbility)

    suspend fun saveCrewUpgrade(crewUpgrade: CrewUpgrade) =
        scoundrelUseCase.saveCrewUpgrade(crewUpgrade)

    suspend fun deleteCrewUpgrade(crewUpgrade: CrewUpgrade) =
        scoundrelUseCase.deleteCrewUpgrade(crewUpgrade)


    /* val crewAbilityListUiState: StateFlow<CrewAbilityListUiState> =
         scoundrelUseCase.getListOfCrewAbilities()
             .map { CrewAbilityListUiState(it) }
             .stateIn(
                 scope = viewModelScope,
                 started = SharingStarted.WhileSubscribed(500L),
                 initialValue = CrewAbilityListUiState()
             )*/


}//end ViewModel

data class AbilityListUiState(
    val list: List<SpecialAbility> = listOf()
)

data class CrewAbilityListUiState(
    val crewAbilityList: List<CrewAbility> = listOf()
)
