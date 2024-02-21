package uk.co.maddwarf.notesinthenight.ui.screens.scoundrel

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
import uk.co.maddwarf.notesinthenight.R
import uk.co.maddwarf.notesinthenight.model.Contact
import uk.co.maddwarf.notesinthenight.model.Crew
import uk.co.maddwarf.notesinthenight.model.Scoundrel
import uk.co.maddwarf.notesinthenight.model.SpecialAbility
import uk.co.maddwarf.notesinthenight.navigation.NavigationDestination

object ScoundrelEntryDestination: NavigationDestination{
    override val route="scoundrel_entry"
    override val titleRes = R.string.scoundrel_entry
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoundrelEntryScreen(
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToScoundrelList:()->Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack:Boolean = true,
    viewModel: ScoundrelEntryViewModel = hiltViewModel()
){
    val uiState = viewModel.scoundrelEntryUiState
    val coroutineScope = rememberCoroutineScope()

    //define lists here
    val scoundrelList by viewModel.scoundrelList.collectAsState(listOf())

    var playbookList = mutableListOf<String>()
    scoundrelList.forEach {
        playbookList.add(it.playbook)
    }
    playbookList = playbookList.distinct().toMutableList()

    val heritageList = mutableListOf<String>()
    scoundrelList.forEach {
        heritageList.add(it.heritage)
    }

    val backgroundList = mutableListOf<String>()
    scoundrelList.forEach {
        backgroundList.add(it.background)
    }

    val everyAbilityList by viewModel.abilityList.collectAsState(initial = listOf())
    val everyContactList by viewModel.contactList.collectAsState(listOf())
    val crewList by viewModel.crewList.collectAsState(listOf())

    Scaffold(
        topBar = {
            NotesInTheNightTopAppBar(
                title = stringResource(ScoundrelEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp,
                navigateToHome = navigateToHome
            )
        }
    ) { innerPadding ->
        ScoundrelEntryBody(
            entryUiState = uiState,
            onValueChange = viewModel::updateUiStateEntry,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveItem()
                    navigateToScoundrelList()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            playbookList = playbookList,
            crewList = crewList,
            heritageList = heritageList,
            backgroundList = backgroundList,
            everyAbilityList = everyAbilityList,
            everyContactList = everyContactList,
        )
    }//end scaffold
}//end scoundrel Entry Screen

@Composable
fun ScoundrelEntryBody(
    entryUiState: ScoundrelEntryUiState,
    onValueChange: (Scoundrel) -> Unit = {},
    onSaveClick: () -> Unit,
    modifier: Modifier,
    playbookList: List<String>,
    heritageList: List<String>,
    backgroundList: List<String>,
    everyAbilityList: List<SpecialAbility>,
    everyContactList: List<Contact>,
    crewList: List<Crew>,
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
            modifier = modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),

            ) {
            ScoundrelInputForm(
                scoundrelDetails = entryUiState.scoundrelDetails,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                playbookList = playbookList,
                everyCrewList = crewList,
                heritageList = heritageList,
                backgroundList = backgroundList,
                everyAbilityList = everyAbilityList,
                everyContactList = everyContactList,
            )
            Button(
                onClick = onSaveClick,
                enabled = entryUiState.isEntryValid,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Text(text = stringResource(R.string.save_action))
            }
        }//emd column
    }//end outer box
}//end scoundrel entry body