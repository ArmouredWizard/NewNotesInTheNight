package uk.co.maddwarf.notesinthenight.ui.screens.ability

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import uk.co.maddwarf.notesinthenight.NotesInTheNightTopAppBar
import uk.co.maddwarf.notesinthenight.R
import uk.co.maddwarf.notesinthenight.model.CrewAbility
import uk.co.maddwarf.notesinthenight.model.CrewUpgrade
import uk.co.maddwarf.notesinthenight.model.FabItem
import uk.co.maddwarf.notesinthenight.model.SpecialAbility
import uk.co.maddwarf.notesinthenight.navigation.NavigationDestination
import uk.co.maddwarf.notesinthenight.ui.composables.DeleteConfirmationDialog
import uk.co.maddwarf.notesinthenight.ui.composables.FabEntry
import uk.co.maddwarf.notesinthenight.ui.composables.InfoPopUp
import uk.co.maddwarf.notesinthenight.ui.composables.NameAndDescrEntryDialog
import uk.co.maddwarf.notesinthenight.ui.composables.TitleBlock
import uk.co.maddwarf.notesinthenight.ui.composables.ability.AbilityItem
import uk.co.maddwarf.notesinthenight.ui.screens.crew.CrewAbilityItem
import uk.co.maddwarf.notesinthenight.ui.screens.crew.CrewUpgradeItem

object AbilityListDestination : NavigationDestination {
    override val route = "ability_list"
    override val titleRes = R.string.ability_list
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbilityListScreen(
    navigateToHome: () -> Unit,
    onNavigateUp: () -> Unit,
    abilityListViewModel: AbilityListViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    //  val uiState by abilityListViewModel.abilityListUiState.collectAsState()
    //   val crewUiState by abilityListViewModel.crewAbilityListUiState.collectAsState()

    val everyAbilityList by abilityListViewModel.abilityList.collectAsState(initial = listOf())
    val crewAbilityList by abilityListViewModel.crewAbilityList.collectAsState(initial = listOf())
    val crewUpgradeList by abilityListViewModel.crewUpgradeList.collectAsState(listOf())

    var newAbility by remember { mutableStateOf(SpecialAbility()) }
    var showAddAbilityDialog by remember { mutableStateOf(false) }
    fun addAbilityClick() {
        Log.d("CLICK", "ADD ABILITY CLICKED!")
        showAddAbilityDialog = !showAddAbilityDialog
    }

    fun onAbilityChange(abilityName: String) {
        newAbility = newAbility.copy(abilityName = abilityName)
    }

    fun onDescriptionChange(abilityDescription: String) {
        newAbility = newAbility.copy(abilityDescription = abilityDescription)
    }

    fun doNewAbilityFromPair(info: Pair<String, String>) {
        showAddAbilityDialog = false
        coroutineScope.launch {
            abilityListViewModel.saveAbility(
                SpecialAbility(
                    abilityName = info.first,
                    abilityDescription = info.second
                )
            )
        }
        newAbility = SpecialAbility()
    }

    if (showAddAbilityDialog) {
        NameAndDescrEntryDialog(
            title = "Add Special Ability",
            name = newAbility.abilityName,
            nameLabel = "Ability Name",
            nameHint = "Ability Name",
            description = newAbility.abilityDescription,
            descriptionLabel = "Ability Description",
            descriptionHint = "Describe the new Ability",
            onDismiss = { showAddAbilityDialog = !showAddAbilityDialog },
            onAccept = { doNewAbilityFromPair(it) },
            onNameChange = { onAbilityChange(it) },
            onDescriptionChange = { onDescriptionChange(it) },
        )

    }

    //start duplicate code

    var newCrewAbility by remember { mutableStateOf(CrewAbility()) }
    var showAddCrewAbilityDialog by remember { mutableStateOf(false) }
    fun addCrewAbilityClick() {
        showAddCrewAbilityDialog = !showAddCrewAbilityDialog
    }

    fun onCrewAbilityChange(crewAbilityName: String) {
        newCrewAbility = newCrewAbility.copy(crewAbilityName = crewAbilityName)
    }

    fun onCrewDescriptionChange(crewAbilityDescription: String) {
        newCrewAbility = newCrewAbility.copy(crewAbilityDescription = crewAbilityDescription)
    }

    fun doNewCrewAbilityFromPair(info: Pair<String, String>) {
        showAddCrewAbilityDialog = false
        coroutineScope.launch {
            abilityListViewModel.saveCrewAbility(
                CrewAbility(
                    crewAbilityName = info.first,
                    crewAbilityDescription = info.second
                )
            )
        }
        newCrewAbility = CrewAbility()
    }

    if (showAddCrewAbilityDialog) {
        NameAndDescrEntryDialog(
            title = "Add Crew Ability",
            name = newCrewAbility.crewAbilityName,
            nameLabel = "Crew Ability Name",
            nameHint = "The name of the Ability",
            description = newCrewAbility.crewAbilityDescription,
            descriptionLabel = "Crew Ability Description",
            descriptionHint = "Describe the new Crew Ability",
            onDismiss = { showAddCrewAbilityDialog = !showAddCrewAbilityDialog },
            onAccept = { doNewCrewAbilityFromPair(it) },
            onNameChange = { onCrewAbilityChange(it) },
            onDescriptionChange = { onCrewDescriptionChange(it) },
        )
    }

    //start Upgrade Duplicate code

    var newCrewUpgrade by remember { mutableStateOf(CrewUpgrade()) }
    var showAddCrewUpgradeDialog by remember { mutableStateOf(false) }
    fun addCrewUpgradeClick() {
        showAddCrewUpgradeDialog = !showAddCrewUpgradeDialog
    }

    fun onCrewUpgradeChange(crewUpgradeName: String) {
        newCrewUpgrade = newCrewUpgrade.copy(upgradeName = crewUpgradeName)
    }

    fun onUpgradeDescriptionChange(upgradeDescription: String) {
        newCrewUpgrade = newCrewUpgrade.copy(upgradeDescription = upgradeDescription)
    }

    fun doNewCrewUpgradeFromPair(info: Pair<String, String>) {
        showAddCrewUpgradeDialog = false
        coroutineScope.launch {
            abilityListViewModel.saveCrewUpgrade(
                CrewUpgrade(
                    upgradeName = info.first,
                    upgradeDescription = info.second
                )
            )
        }
        newCrewUpgrade = CrewUpgrade()
    }

    if (showAddCrewUpgradeDialog) {
        NameAndDescrEntryDialog(
            title = "Add Crew Upgrade",
            name = newCrewUpgrade.upgradeName,
            nameLabel = "Crew Upgrade Name",
            nameHint = "The name of the Upgrade",
            description = newCrewUpgrade.upgradeDescription,
            descriptionLabel = "Crew Upgrade Description",
            descriptionHint = "Describe the new Crew Upgrade",
            onDismiss = { showAddCrewUpgradeDialog = !showAddCrewUpgradeDialog },
            onAccept = { doNewCrewUpgradeFromPair(it) },
            onNameChange = { onCrewUpgradeChange(it) },
            onDescriptionChange = { onUpgradeDescriptionChange(it) },
        )

    }

    var showMenu by remember { mutableStateOf(false) }
    fun showMenuClick() {
        showMenu = !showMenu
    }

    val menuList = listOf<FabItem>(
        FabItem(
            label = "Add Special Ability",
            leadingIcon = Icons.Default.Add,
            clickFunction = { addAbilityClick();showMenuClick() }),
        FabItem(
            label = "Add Crew Ability",
            leadingIcon = Icons.Default.Add,
            clickFunction = { addCrewAbilityClick();showMenuClick() }),
        FabItem(
            label = "Add Crew Upgrade",
            leadingIcon = Icons.Default.Add,
            clickFunction = { addCrewUpgradeClick();showMenuClick() }),
    )

    Scaffold(
        topBar = {
            NotesInTheNightTopAppBar(
                title = "List of Special Abilities",
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                navigateToHome = navigateToHome
            )
        },
        floatingActionButton = {
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.End
                ) {
                    if (showMenu) {
                        FabMenu(menuList)
                    }
                    FloatingActionButton(
                        onClick = { showMenuClick() },
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add"
                        )
                    }
                }//end column
            }//end Box
        }//end fab,
    ) { innerPadding ->
        AbilityListBody(
            abilityListViewModel = abilityListViewModel,
            abilityList = everyAbilityList,
            crewAbilityList = crewAbilityList,
            crewUpgradeList = crewUpgradeList,
            onItemClick = {},
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }//end Scaffold
}//end Ability List Screen

@Composable
fun FabMenu(menuList: List<FabItem>) {
    Log.d("FAB MENU", "Clicked")
    menuList.forEach {
        FabEntry(
            title = it.label,
            leadingIcon = it.leadingIcon,
            onClick = it.clickFunction
        )
    }
}

@Composable
fun AbilityListBody(
    abilityListViewModel: AbilityListViewModel,
    abilityList: List<SpecialAbility>,
    crewAbilityList: List<CrewAbility>,
    crewUpgradeList: List<CrewUpgrade>,
    onItemClick: (SpecialAbility) -> Unit,
    modifier: Modifier
) {

    val coroutineScope = rememberCoroutineScope()

    var displayDeleteAbilityDialog by remember { mutableStateOf(false) }
    var chosenAbility by remember { mutableStateOf(SpecialAbility()) }

    fun doRemoveAbility(ability: SpecialAbility) {
        displayDeleteAbilityDialog = false
        coroutineScope.launch {
            abilityListViewModel.deleteAbility(ability)
        }
    }

    fun showDeleteScoundrelAbilityDialog(ability: SpecialAbility) {
        chosenAbility = ability
        displayDeleteAbilityDialog = !displayDeleteAbilityDialog
    }

    if (displayDeleteAbilityDialog) {
        DeleteConfirmationDialog(
            title = "Special Ability",
            name = chosenAbility.abilityName,
            onAccept = { doRemoveAbility(chosenAbility) },
            onDismiss = { displayDeleteAbilityDialog = false }
        )
    }

    var showAbilityPopUp by remember { mutableStateOf(false) }
    var chosenAbilityToShow by remember { mutableStateOf(SpecialAbility()) }
    fun doAbilityPopUp(ability: SpecialAbility) {
        chosenAbilityToShow = ability
        showAbilityPopUp = !showAbilityPopUp
    }

    if (showAbilityPopUp) {
        InfoPopUp(
            title = "Special Ability",
            firstTextTitle = "Special Ability Name",
            firstText = chosenAbilityToShow.abilityName,
            secondTextTitle = "Special Ability Description",
            secondText = chosenAbilityToShow.abilityDescription,
            onDismiss = { showAbilityPopUp = !showAbilityPopUp }
        )
    }

    //start Duplicate Crew delete Code

    var displayDeleteCrewAbilityDialog by remember { mutableStateOf(false) }
    var chosenCrewAbility by remember { mutableStateOf(CrewAbility()) }

    fun doRemoveCrewAbility(ability: CrewAbility) {
        displayDeleteCrewAbilityDialog = false
        coroutineScope.launch {
            abilityListViewModel.deleteCrewAbility(ability)
        }
    }

    fun showDeleteCrewAbilityDialog(ability: CrewAbility) {
        chosenCrewAbility = ability
        displayDeleteCrewAbilityDialog = !displayDeleteCrewAbilityDialog
    }

    if (displayDeleteCrewAbilityDialog) {
        DeleteConfirmationDialog(
            title = "Crew Ability",
            name = chosenCrewAbility.crewAbilityName,
            onAccept = { doRemoveCrewAbility(chosenCrewAbility) },
            onDismiss = { displayDeleteAbilityDialog = false }
        )
    }

    var showCrewAbilityPopUp by remember { mutableStateOf(false) }
    var chosenCrewAbilityToShow by remember { mutableStateOf(CrewAbility()) }
    fun doCrewAbilityPopUp(ability: CrewAbility) {
        chosenCrewAbilityToShow = ability
        showCrewAbilityPopUp = !showCrewAbilityPopUp
    }

    if (showCrewAbilityPopUp) {
        InfoPopUp(
            title = "Crew Ability",
            firstTextTitle = "Crew Ability Name",
            firstText = chosenCrewAbilityToShow.crewAbilityName,
            secondTextTitle = "Crew Ability Description",
            secondText = chosenCrewAbilityToShow.crewAbilityDescription,
            onDismiss = { showCrewAbilityPopUp = !showCrewAbilityPopUp }
        )
    }

    //start Duplicate Crew delete Code

    var displayDeleteCrewUpgradeDialog by remember { mutableStateOf(false) }
    var chosenCrewUpgrade by remember { mutableStateOf(CrewUpgrade()) }

    fun doRemoveCrewUpgrade(upgrade: CrewUpgrade) {
        displayDeleteCrewUpgradeDialog = false
        coroutineScope.launch {
            abilityListViewModel.deleteCrewUpgrade(upgrade)
        }
    }

    fun showDeleteCrewUpgradeDialog(upgrade: CrewUpgrade) {
        chosenCrewUpgrade = upgrade
        displayDeleteCrewUpgradeDialog = !displayDeleteCrewUpgradeDialog
    }

    if (displayDeleteCrewUpgradeDialog) {
        DeleteConfirmationDialog(
            title = "Crew Upgrade",
            name = chosenCrewUpgrade.upgradeName,
            onAccept = { doRemoveCrewUpgrade(chosenCrewUpgrade) },
            onDismiss = { displayDeleteCrewUpgradeDialog = false }
        )
    }

    var showCrewUpgradePopUp by remember { mutableStateOf(false) }
    var chosenCrewUpgradeToShow by remember { mutableStateOf(CrewUpgrade()) }
    fun doCrewUpgradePopUp(upgrade: CrewUpgrade) {
        chosenCrewUpgradeToShow = upgrade
        showCrewUpgradePopUp = !showCrewUpgradePopUp
    }

    if (showCrewUpgradePopUp) {
        InfoPopUp(
            title = "Crew Upgrade",
            firstTextTitle = "Crew Upgrade Name",
            firstText = chosenCrewUpgradeToShow.upgradeName,
            secondTextTitle = "Crew Upgrade Description",
            secondText = chosenCrewUpgradeToShow.upgradeDescription,
            onDismiss = { showCrewUpgradePopUp = !showCrewUpgradePopUp }
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .paint(painterResource(id = R.drawable.cobbles), contentScale = ContentScale.FillBounds)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(10.dp))

//FILTERS GO HERE
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item {
                    Row() {
                        TitleBlock(title = "Scoundrel Special Abilities", text = "")
                    }
                }
                if (abilityList.isEmpty()) {
                    item {
                        Text(text = "NO ABILITIES")
                    }
                } else {
                    items(items = abilityList) { item ->
                        AbilityItem(
                            ability = item,
                            modifier = Modifier
                                .padding(6.dp),
                            displayDeleteAbilityDialog = { showDeleteScoundrelAbilityDialog(item) },
                            onClick = { doAbilityPopUp(item) }
                        )
                    }
                }//end IF List
                item {
                    Row() {
                        TitleBlock(title = "Crew Special Abilities", text = "")
                    }
                }
                if (crewAbilityList.isEmpty()) {
                    item {
                        Text(text = "NO CREW ABILITIES")
                    }
                } else {
                    items(
                        items = crewAbilityList,
                    ) { item ->
                        CrewAbilityItem(
                            ability = item,
                            modifier = Modifier
                                .padding(6.dp),
                            displayDeleteAbilityDialog = { showDeleteCrewAbilityDialog(item) },
                            onClick = { doCrewAbilityPopUp(item) }
                        )
                    }
                }
                item {
                    Row() {
                        TitleBlock(title = "Crew Upgrades", text = "")
                    }
                }
                if (crewUpgradeList.isEmpty()) {
                    item {
                        Text(text = "NO CREW UPGRADES")
                    }
                } else {
                    items(
                        items = crewUpgradeList,
                    ) { item ->
                        CrewUpgradeItem(
                            upgrade = item,
                            modifier = Modifier
                                .padding(6.dp),
                            displayDeleteUpgradeDialog = { showDeleteCrewUpgradeDialog(item) },
                            onClick = { doCrewUpgradePopUp(item) }
                        )
                    }
                }
            }//end lazy column
        }//end column
    }//end box
}//end Scoundrel List Body