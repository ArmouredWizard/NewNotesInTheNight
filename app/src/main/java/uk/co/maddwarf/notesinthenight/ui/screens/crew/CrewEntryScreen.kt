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

object CrewEntryDestination : NavigationDestination {
    override val route = "crew_entry"
    override val titleRes = R.string.crew_entry
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrewEntryScreen(
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    navigateToCrewDetails: (Int) -> Unit,
    viewModel: CrewEntryViewModel = hiltViewModel()

) {
    val coroutineScope = rememberCoroutineScope()

    val crewList by viewModel.crewList.collectAsState(initial = listOf())
    val everyContactList by viewModel.contactList.collectAsState(initial = listOf())
    val everyUpgradeList by viewModel.upgradeList.collectAsState(initial = listOf())
    val crewAbilitiesList by viewModel.crewAbilityList.collectAsState(initial = listOf())

    var crewReputationList = mutableListOf<String>()
    var crewTypeList = mutableListOf<String>()

    crewList.forEach {
        crewTypeList.add(it.crewType)
        crewReputationList.add(it.reputation)
    }
    crewTypeList = crewTypeList.distinct().toMutableList()
    crewReputationList = crewReputationList.distinct().toMutableList()

    //scaffold here
    Scaffold(
        topBar = {
            NotesInTheNightTopAppBar(
                title = "Enter Crew Details",
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp,
                navigateToHome = navigateToHome
            )
        }
    ) { innerPadding ->
        CrewEntryBody(
            crewUiState = viewModel.crewEntryUiState,
            onItemValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveFullCrew()
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

}//end Crew Entry Screen

@Composable
fun CrewEntryBody(
    crewUiState: CrewEntryUiState,
    crewTypeList: List<String>,
    crewReputationList: List<String>,
    crewAbilitiesList: List<CrewAbility>,
    everyContactList: List<Contact>,
    onItemValueChange: (Crew) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    everyUpgradeList: List<CrewUpgrade>,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .paint(
                painterResource(id = R.drawable.cobbles),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .padding(10.dp)
        ) {
            CrewInputForm(
                crewDetails = crewUiState.crewDetails,
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