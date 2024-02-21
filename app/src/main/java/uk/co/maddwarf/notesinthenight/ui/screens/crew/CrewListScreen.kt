package uk.co.maddwarf.notesinthenight.ui.screens.crew

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import uk.co.maddwarf.notesinthenight.NotesInTheNightTopAppBar
import uk.co.maddwarf.notesinthenight.R
import uk.co.maddwarf.notesinthenight.model.Crew
import uk.co.maddwarf.notesinthenight.navigation.NavigationDestination
import uk.co.maddwarf.notesinthenight.ui.composables.DeleteConfirmationDialog
import uk.co.maddwarf.notesinthenight.ui.composables.MySpinner
import uk.co.maddwarf.notesinthenight.ui.screens.scoundrel.CrewItem

object CrewListDestination : NavigationDestination {
    override val route = "crew_list"
    override val titleRes = R.string.crew_list
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrewListScreen(
    navigateToHome: () -> Unit,
    onNavigateUp: () -> Unit,
    navigateToCrewEntry: () -> Unit,
    navigateToCrewDetails: (Int) -> Unit,
    crewListViewModel: CrewListViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()

    val uiState by crewListViewModel.crewListUiState.collectAsState()

    var showDeleteCrewDialog by remember { mutableStateOf(false) }
    var chosenCrew by remember { mutableStateOf(Crew()) }

    fun doDeleteCrew(crew: Crew) {
        showDeleteCrewDialog = false
        coroutineScope.launch {
            crewListViewModel.deleteCrew(crew)
        }
    }

    fun displayDeleteCrewDialog(crew: Crew) {
        chosenCrew = crew
        showDeleteCrewDialog = true
    }
    if (showDeleteCrewDialog) {
        DeleteConfirmationDialog(
            title = "Crew",
            name = chosenCrew.crewName,
            onAccept = { doDeleteCrew(chosenCrew) },
            onDismiss = { showDeleteCrewDialog = false }
        )
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NotesInTheNightTopAppBar(
                title = "List of Crews",
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior,
                navigateToHome = navigateToHome
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToCrewEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
    ) { innerPadding ->
        CrewListBody(
            crewList = uiState.list,
            onItemClick = navigateToCrewDetails,
            displayDeleteCrewDialog = { displayDeleteCrewDialog(it) },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }//end Scaffold

}//end scoundrel list screen

@Composable
fun CrewListBody(
    crewList: List<Crew>,
    onItemClick: (Int) -> Unit,
    displayDeleteCrewDialog: (Crew) -> Unit,
    modifier: Modifier
) {

    var chosenType by remember { mutableStateOf("All Crew Types") }
    val typeList = mutableListOf<String>()
    crewList.forEach { crew ->
        if (crew.crewType != "") {
            typeList.add(crew.crewType)
        }
    }
    typeList.add("All Crew Types")

    var typeFilterExpanded by remember { mutableStateOf(false) }

    fun crewTypeChooser(type: String) {
        typeFilterExpanded = false
        chosenType = type
    }

    Spacer(modifier = Modifier.height(10.dp))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .paint(painterResource(id = R.drawable.cobbles), contentScale = ContentScale.FillBounds)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                MySpinner(
                    expanded = typeFilterExpanded,
                    onClick = { typeFilterExpanded = !typeFilterExpanded },
                    list = typeList,
                    chooser = ::crewTypeChooser,
                    report = chosenType
                )
            }

            val filteredCrewList = if (chosenType == "All Crew Types") {
                crewList
            } else {
                crewList.filter { it.crewType == chosenType }.distinct().toMutableList()
            }

            if (crewList.isEmpty()) {
                Text(text = "NO CREWS")
            } else {
//FILTERS GO HERE
                LazyColumn(modifier = Modifier) {
                    items(items = filteredCrewList, key = { it.crewId }) { item ->
                        CrewItem(
                            crew = item,
                            modifier = Modifier
                                .padding(6.dp)
                                .clickable { },
                            displayDeleteCrewDialog = displayDeleteCrewDialog,
                            onClick = {
                                Log.d("CREW IN LIST", it.crewId.toString())
                                onItemClick(it.crewId)
                            }
                        )
                    }
                }
            }//end IF List
        }//end column
    }//end Box
}//end Crew List Body
