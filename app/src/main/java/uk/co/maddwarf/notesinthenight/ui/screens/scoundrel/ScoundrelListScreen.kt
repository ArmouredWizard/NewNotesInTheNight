package uk.co.maddwarf.notesinthenight.ui.screens.scoundrel

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
import uk.co.maddwarf.notesinthenight.model.Scoundrel
import uk.co.maddwarf.notesinthenight.navigation.NavigationDestination
import uk.co.maddwarf.notesinthenight.ui.composables.DeleteConfirmationDialog
import uk.co.maddwarf.notesinthenight.ui.composables.crew.MyCrewSpinner
import uk.co.maddwarf.notesinthenight.ui.composables.MySpinner
import uk.co.maddwarf.notesinthenight.ui.composables.scoundrel.ScoundrelItem

object ScoundrelListDestination : NavigationDestination {
    override val route = "scoundrel_list"
    override val titleRes = R.string.scoundrel_list
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoundrelListScreen(
    navigateToHome: () -> Unit,
    onNavigateUp: () -> Unit,
    navigateToScoundrelEntry: () -> Unit,
    navigateToScoundrelDetails: (Int) -> Unit,
    scoundrelListViewModel: ScoundrelListViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()

    val uiState by scoundrelListViewModel.scoundrelListUiState.collectAsState()

    var showDeleteScoundrelDialog by remember { mutableStateOf(false) }
    var chosenScoundrel by remember { mutableStateOf(Scoundrel()) }

    fun doDeleteScoundrel(scoundrel: Scoundrel) {
        showDeleteScoundrelDialog = false
        coroutineScope.launch {
            scoundrelListViewModel.deleteScoundrel(scoundrel)
        }
    }

    fun displayDeleteScoundrelDialog(scoundrel: Scoundrel) {
        chosenScoundrel = scoundrel
        showDeleteScoundrelDialog = true
    }
    if (showDeleteScoundrelDialog) {
        DeleteConfirmationDialog(
            title = "Scoundrel",
            name = chosenScoundrel.name,
            onAccept = { doDeleteScoundrel(chosenScoundrel) },
            onDismiss = { showDeleteScoundrelDialog = false }
        )
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NotesInTheNightTopAppBar(
                title = "List of Scoundrels",
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior,
                navigateToHome = navigateToHome
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToScoundrelEntry,
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
        ScoundrelListBody(
            scoundrelList = uiState.list,
            onItemClick = navigateToScoundrelDetails,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            displayDeleteScoundrelDialog = { displayDeleteScoundrelDialog(it) },
            navigateToScoundrelDetails = navigateToScoundrelDetails
        )
    }//end Scaffold
}//end scoundrel list screen

@Composable
fun ScoundrelListBody(
    scoundrelList: List<Scoundrel>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier,
    displayDeleteScoundrelDialog: (Scoundrel) -> Unit,
    navigateToScoundrelDetails: (Int) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .paint(painterResource(id = R.drawable.cobbles), contentScale = ContentScale.FillBounds),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        ) {

            var chosenCrew by remember { mutableStateOf(Crew(crewName = "All Crews")) }
            val crewList = mutableListOf<Crew>(Crew(crewName = "All Crews"))
            scoundrelList.forEach { scoundrel ->
                if (scoundrel.crew!=null) {
                    Log.d("ADDING CREW", scoundrel.crew!!.crewName)
                    crewList.add(scoundrel.crew!!)
                }
            }

            var crewFilterExpanded by remember { mutableStateOf(false) }

            fun crewChooser(crew: Crew) {
                crewFilterExpanded = false
                chosenCrew = crew
                Log.d("Crew ", chosenCrew.crewName)
            }

            var chosenPlaybook by remember { mutableStateOf("All Playbooks") }
            val playbookList = mutableListOf<String>("All Playbooks")
            scoundrelList.forEach { scoundrel ->
                if (scoundrel.playbook != "") {
                    Log.d("ADDING PLaybook", scoundrel.playbook)
                    playbookList.add(scoundrel.playbook)
                }
            }
            Log.d("PLAYLIST", playbookList.toString())

            var playbookFilterExpanded by remember { mutableStateOf(false) }

            fun playbookChooser(playbook: String) {
                playbookFilterExpanded = false
                chosenPlaybook = playbook
                Log.d("playbook ", playbook)
            }

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                //horizontalArrangement = Arrangement.SpaceEvenly
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center
                ){
                    MyCrewSpinner(
                        expanded = crewFilterExpanded,
                        onClick = { crewFilterExpanded = !crewFilterExpanded },
                        list = crewList.distinct(),
                        chooser = ::crewChooser,
                        report = chosenCrew.crewName
                    )
                }
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center
                ){
                    MySpinner(
                        expanded = playbookFilterExpanded,
                        onClick = { playbookFilterExpanded = !playbookFilterExpanded },
                        list = playbookList.distinct(),
                        chooser = ::playbookChooser,
                        report = chosenPlaybook
                    )}

            }//end filter row

            val crewFilteredScoundrelList: MutableList<Scoundrel> =
                if (chosenCrew.crewName == "All Crews") {
                    scoundrelList.toMutableList()
                } else {
                    scoundrelList.filter { it.crew == chosenCrew }.distinct().toMutableList()
                }

            //todo adjust for more filters

            val filteredScoundrelList = if (chosenPlaybook == "All Playbooks") {
                crewFilteredScoundrelList
            } else {
                crewFilteredScoundrelList.filter { it.playbook == chosenPlaybook }.distinct()
                    .toMutableList()
            }

            if (filteredScoundrelList.isEmpty()) {
                Text(text = "NO SCOUNDRELS")
            } else {
//FILTERS GO HERE
                LazyColumn(modifier = Modifier) {
                    items(items = filteredScoundrelList, key = { it.scoundrelId }) { item ->
                        Log.d("SCOUNDREL ITEM", item.toString())
                        ScoundrelItem(
                            scoundrel = item,
                            modifier = Modifier
                                .padding(6.dp)
                                .clickable { onItemClick(item.scoundrelId) },
                            displayDeleteScoundrelDialog = { displayDeleteScoundrelDialog(it) },
                            onClick = { navigateToScoundrelDetails(it.scoundrelId) }
                        )
                    }
                }
            }//end IF List
        }//end column
    }//end Box
}//end Scoundrel List Body