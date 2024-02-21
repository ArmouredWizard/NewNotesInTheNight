package uk.co.maddwarf.notesinthenight.ui.screens.crew

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import uk.co.maddwarf.notesinthenight.NotesInTheNightTopAppBar
import uk.co.maddwarf.notesinthenight.R
import uk.co.maddwarf.notesinthenight.model.Contact
import uk.co.maddwarf.notesinthenight.model.Crew
import uk.co.maddwarf.notesinthenight.model.CrewAbility
import uk.co.maddwarf.notesinthenight.model.CrewUpgrade
import uk.co.maddwarf.notesinthenight.navigation.NavigationDestination

object CrewEditDestination : NavigationDestination {
    override val route = "crew_edit"
    override val titleRes: Int = R.string.crew_edit_title

    const val itemIdArg = "crew_id"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrewEditScreen(
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: CrewEditViewModel = hiltViewModel()
) {

    val coroutineScope = rememberCoroutineScope()

    val editUiState = viewModel.editUiState.collectAsState()
    val uIState = viewModel.intermediateCrewUiState

    val crewList by viewModel.crewList.collectAsState(initial = listOf())
    val everyContactList by viewModel.contactList.collectAsState(initial = listOf())
    val crewAbilitiesList by viewModel.crewAbilityList.collectAsState(initial = listOf())
    val everyUpgradeList by viewModel.upgradeList.collectAsState(listOf())

    var crewReputationList = mutableListOf<String>()
    var crewTypeList = mutableListOf<String>()

    crewList.forEach {
        crewTypeList.add(it.crewType)
        crewReputationList.add(it.reputation)
    }
    crewTypeList = crewTypeList.distinct().toMutableList()
    crewReputationList = crewReputationList.distinct().toMutableList()

    var title = editUiState.value.crewDetails.crewName
    if (uIState.intermediateCrewDetails.crewId == 0) {
        viewModel.initialise()
        title = "New Scoundrel"
    }

    Scaffold(
        topBar = {
            NotesInTheNightTopAppBar(
                title = title,
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                navigateToHome = navigateToHome
            )
        }
    ) { innerPadding ->
        CrewEditBody(
            crewUiState = uIState,
            onItemValueChange = viewModel::updateIntermediateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveItem()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            crewTypeList = crewTypeList,
            crewReputationList = crewReputationList,
            crewAbilitiesList = crewAbilitiesList,
            everyContactList = everyContactList,
            everyUpgradeList = everyUpgradeList,
        )
    }//end scaffold
}//end crew edit screen

@Composable
fun CrewEditBody(
    crewUiState: IntermediateCrewUiState,
    crewTypeList: List<String>,
    crewReputationList: List<String>,
    crewAbilitiesList: List<CrewAbility>,
    everyContactList: List<Contact>,
    everyUpgradeList: List<CrewUpgrade>,
    onItemValueChange: (Crew) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .paint(painterResource(id = R.drawable.cobbles), contentScale = ContentScale.FillBounds)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .padding(10.dp)
        ) {
            CrewInputForm(
                crewDetails = crewUiState.intermediateCrewDetails,
                onValueChange = onItemValueChange,
                crewTypeList = crewTypeList,
                crewReputationList = crewReputationList,
                everyAbilityList = crewAbilitiesList,
                everyContactList = everyContactList,
                everyUpgradeList = everyUpgradeList,
            )
            Button(
                onClick = onSaveClick,
                enabled = crewUiState.isEntryValid,
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Save",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.LightGray
                )
            }
        }
    }
}//end CrewEntryBody