package uk.co.maddwarf.notesinthenight.ui.screens.scoundrel

import android.util.Log
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import uk.co.maddwarf.notesinthenight.NotesInTheNightTopAppBar
import uk.co.maddwarf.notesinthenight.model.Scoundrel
import uk.co.maddwarf.notesinthenight.navigation.NavigationDestination
import uk.co.maddwarf.notesinthenight.R
import uk.co.maddwarf.notesinthenight.model.Contact
import uk.co.maddwarf.notesinthenight.model.Crew
import uk.co.maddwarf.notesinthenight.model.SpecialAbility

object ScoundrelEditDestination : NavigationDestination {
    override val route = "scoundrel_edit"
    override val titleRes: Int = R.string.scoundrel_edit_title

    const val itemIdArg = "scoundrel_id"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoundrelEditScreen(
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: ScoundrelEditViewModel = hiltViewModel()
) {
    val editUiState = viewModel.editUiState.collectAsState()
    val uIState = viewModel.intermediateScoundrelUiState

    val scoundrelList by viewModel.scoundrelList.collectAsState(listOf())

    var title = editUiState.value.scoundrelDetails.name

    var playbookList = mutableListOf<String>()
    scoundrelList.forEach {
        playbookList.add(it.playbook)
    }
    playbookList = playbookList.distinct().toMutableList()

    var heritageList = mutableListOf<String>()
    scoundrelList.forEach {
        heritageList.add(it.heritage)
    }
    heritageList = heritageList.distinct().toMutableList()

    var backgroundList = mutableListOf<String>()
    scoundrelList.forEach {
        backgroundList.add(it.background)
    }
    backgroundList = backgroundList.distinct().toMutableList()

    val everyAbilityList by viewModel.abilityList.collectAsState(initial = listOf())
    val everyContactList by viewModel.contactList.collectAsState(listOf())
    val everyCrewList by viewModel.crewList.collectAsState(listOf())

    val coroutineScope = rememberCoroutineScope()

    if (uIState.intermediateScoundrelDetails.scoundrelId == 0) {
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
        ScoundrelEditBody(
            editUiState = uIState,
            onValueChange = viewModel::updateIntermediateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveItem()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .paint(
                    painterResource(id = R.drawable.cobbles),
                    contentScale = ContentScale.FillBounds
                ),
            everyCrewList = everyCrewList,
            playbookList = playbookList,
            heritageList = heritageList,
            backgroundList = backgroundList,
            everyAbilityList = everyAbilityList,
            everyContactList = everyContactList,
        )
    }//end scaffold

}//end scoundrel edit screen

@Composable
fun ScoundrelEditBody(
    editUiState: IntermediateScoundrelUiState,
    modifier: Modifier,
    onSaveClick: () -> Unit,
    onValueChange: (Scoundrel) -> Unit,
    everyCrewList: List<Crew>,
    playbookList: List<String>,
    heritageList: List<String>,
    backgroundList: List<String>,
    everyAbilityList: List<SpecialAbility>,
    everyContactList: List<Contact>,

    ) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(painterResource(id = R.drawable.cobbles), contentScale = ContentScale.FillBounds)
    ) {
        Column(
            modifier = modifier.padding(10.dp)
            ,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ScoundrelInputForm(
                scoundrelDetails = editUiState.intermediateScoundrelDetails,
                modifier = Modifier.fillMaxSize(),
                onValueChange = onValueChange,
                everyCrewList = everyCrewList,
                playbookList = playbookList,
                heritageList = heritageList,
                backgroundList = backgroundList,
                everyAbilityList = everyAbilityList,
                everyContactList = everyContactList,
            )
            Button(
                onClick = onSaveClick,
                enabled = editUiState.isEntryValid,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Text(
                    text = stringResource(R.string.save_action),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.LightGray
                )
            }
        }
    }
}//end ScoundrelEditBody