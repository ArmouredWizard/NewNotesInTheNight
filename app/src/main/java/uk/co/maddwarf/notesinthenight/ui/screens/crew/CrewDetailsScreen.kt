package uk.co.maddwarf.notesinthenight.ui.screens.crew

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uk.co.maddwarf.notesinthenight.NotesInTheNightTopAppBar
import uk.co.maddwarf.notesinthenight.R
import uk.co.maddwarf.notesinthenight.model.Contact
import uk.co.maddwarf.notesinthenight.model.Crew
import uk.co.maddwarf.notesinthenight.model.CrewAbility
import uk.co.maddwarf.notesinthenight.model.CrewUpgrade
import uk.co.maddwarf.notesinthenight.model.Scoundrel
import uk.co.maddwarf.notesinthenight.navigation.NavigationDestination
import uk.co.maddwarf.notesinthenight.ui.composables.InfoPopUp
import uk.co.maddwarf.notesinthenight.ui.composables.MyButton
import uk.co.maddwarf.notesinthenight.ui.composables.TraitDots
import uk.co.maddwarf.notesinthenight.ui.composables.TraitText
import uk.co.maddwarf.notesinthenight.ui.composables.contact.ContactItem
import uk.co.maddwarf.notesinthenight.ui.composables.scoundrel.ScoundrelItem

object CrewDetailsDestination : NavigationDestination {
    override val route = "crew_details"
    override val titleRes: Int = R.string.crew_detail_title

    const val itemIdArg = "crew_id"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrewDetailsScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToCrewEdit: (Int) -> Unit,
    viewModel: CrewDetailsViewModel = hiltViewModel()
) {
    val uiState = viewModel.detailsUiState.collectAsState()

    val scoundrelList by viewModel.scoundrelList.collectAsState(listOf())

    Scaffold(
        topBar = {
            NotesInTheNightTopAppBar(
                title = uiState.value.crewDetails.crewName,
                canNavigateBack = true,
                navigateUp = navigateBack,
                canNavigateHome = true,
                navigateToHome = navigateToHome
            )
        },
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick =
                {
                    Log.d("ID", uiState.value.crewDetails.crewId.toString())
                    navigateToCrewEdit(uiState.value.crewDetails.crewId)
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Icon"
                )
            }
        },
    ) { innerPadding ->
        CrewDetailsBody(
            detailsUiState = uiState.value,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            scoundrelList = scoundrelList
        )
    }//end scaffold

}//end Crew details screen

@Composable
fun CrewDetailsBody(
    detailsUiState: CrewDetailsUiState,
    modifier: Modifier,
    scoundrelList: List<Scoundrel>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .paint(painterResource(id = R.drawable.cobbles), contentScale = ContentScale.FillBounds)
    ) {
        //show details here
        Column(
            modifier = modifier.padding(10.dp),
        ) {
            var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

            CrewDetails(
                crew = detailsUiState.crewDetails,
                modifier = Modifier.fillMaxWidth(),
                scoundrelList = scoundrelList
            )
        }
    }
}//end detailsBody

@Composable
fun CrewDetails(crew: Crew, modifier: Modifier, scoundrelList: List<Scoundrel>) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.fillMaxWidth(.45f)) {
                TraitText(title = "Type: ", text = crew.crewType)
            }
            Row(modifier = Modifier.fillMaxWidth(.9f)) {
                TraitText(
                    title = "Reputation: ",
                    text = crew.reputation,
                    onClick = {}
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.fillMaxWidth(.45f)) {
                TraitText(title = "HQ: ", text = crew.hq)
            }
            Row(modifier = Modifier.fillMaxWidth(.9f)) {
                TraitText(
                    title = "Hunting Grounds: ",
                    text = crew.huntingGrounds,
                    onClick = {}
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        val strong = if (crew.holdIsStrong) {
            "Strong"
        } else {
            "Weak"
        }
        TraitDots(
            traitName = "Rep",
            traitValue = crew.rep,
            maxValue = 12,
            onDotClicked = {},
            infoText = stringResource(R.string.reputation_info)
        )

        Spacer(modifier = Modifier.height(10.dp))

        TraitDots(
            traitName = "Heat",
            traitValue =crew.heat,
            maxValue = 9,
            onDotClicked = {},
            infoText = stringResource(R.string.heat_info)
        )

        Spacer(modifier = Modifier.height(10.dp))

        TraitDots(
            traitName = "Tier",
            traitValue =  crew.tier,
            maxValue = 4,
            onDotClicked = {},
            infoText = stringResource(R.string.tier_info)
        )

        Spacer(modifier = Modifier.height(5.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Text(
                text = "Hold: $strong",
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = Color.LightGray)
                    .padding(5.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        var showScoundrelPopUp by remember { mutableStateOf(false) }
        var chosenScoundrel by remember { mutableStateOf(Scoundrel()) }
        fun doScoundrelPopUp(scoundrel: Scoundrel) {
            chosenScoundrel = scoundrel
            showScoundrelPopUp = true
        }

        if (showScoundrelPopUp) {
            InfoPopUp(
                title = "Scoundrel",
                firstTextTitle = "Scoundrel Name",
                firstText = chosenScoundrel.name,
                secondTextTitle = "Scoundrel Playbook",
                secondText = chosenScoundrel.playbook,
                onDismiss = { showScoundrelPopUp = false }
            )
        }

        var scoundrelExpanded by remember { mutableStateOf(false) }
        MyButton(
            onClick = { scoundrelExpanded = !scoundrelExpanded },
            text = "Scoundrels",
            trailingIcon = Icons.Default.KeyboardArrowDown
        )
        if (scoundrelExpanded) {
            scoundrelList.forEach { scoundrel ->
                ScoundrelItem(
                    scoundrel = scoundrel,
                    displayDeleteScoundrelDialog = {},
                    enableDelete = false,
                    onClick = { doScoundrelPopUp(it) },
                    showCrew = false
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
        }

        var showAbilityPopUp by remember { mutableStateOf(false) }
        var chosenAbility by remember { mutableStateOf(CrewAbility()) }
        fun doAbilityPopUp(ability: CrewAbility) {
            chosenAbility = ability
            showAbilityPopUp = true
        }

        if (showAbilityPopUp) {
            InfoPopUp(
                title = "Crew Ability",
                firstTextTitle = "Crew Ability Name",
                firstText = chosenAbility.crewAbilityName,
                secondTextTitle = "Crew Ability Description",
                secondText = chosenAbility.crewAbilityDescription,
                onDismiss = { showAbilityPopUp = false }
            )
        }

        var abilityExpanded by remember { mutableStateOf(false) }
        MyButton(
            onClick = { abilityExpanded = !abilityExpanded },
            text = "Crew Abilities",
            trailingIcon = Icons.Default.KeyboardArrowDown
        )
        if (abilityExpanded) {
            crew.crewAbilities.forEach { ability ->
                CrewAbilityItem(
                    ability = ability,
                    displayDeleteAbilityDialog = {},
                    enableDelete = false,
                    onClick = { doAbilityPopUp(it) }
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
        }

        var showContactPopUp by remember { mutableStateOf(false) }
        var chosenContact by remember { mutableStateOf(Contact()) }
        fun doContactPopUp(contact: Contact) {
            chosenContact = contact
            showContactPopUp = true
        }
        if (showContactPopUp) {
            InfoPopUp(
                title = "Contact",
                firstTextTitle = "Contact Name",
                firstText = chosenContact.name,
                secondTextTitle = "Contact Description",
                secondText = chosenContact.description,
                onDismiss = { showContactPopUp = false }
            )
        }

        var contactsExpanded by remember { mutableStateOf(false) }
        MyButton(
            onClick = { contactsExpanded = !contactsExpanded },
            text = "Contacts",
            trailingIcon = Icons.Default.KeyboardArrowDown
        )
        if (contactsExpanded) {
            crew.contacts.forEach { contact ->
                ContactItem(
                    contact = contact,
                    modifier = Modifier
                        .padding(4.dp),
                    enableDelete = false,
                    onClick = { doContactPopUp(it) },
                    displayDeleteContactDialog = {} //displayDeleteDialog
                )
            }//end LazyColumn
        }//end contacts expanded

        var showUpgradePopUp by remember { mutableStateOf(false) }
        var chosenUpgrade by remember { mutableStateOf(CrewUpgrade()) }
        fun doUpgradePopUp(upgrade: CrewUpgrade) {
            chosenUpgrade = upgrade
            showUpgradePopUp = true
        }
        if (showUpgradePopUp) {
            InfoPopUp(
                title = "Crew Upgrade",
                firstTextTitle = "Crew Upgrade Name",
                firstText = chosenUpgrade.upgradeName,
                secondTextTitle = "Crew Upgrade Description",
                secondText = chosenUpgrade.upgradeDescription,
                onDismiss = { showUpgradePopUp = false }
            )
        }

        var upgradesExpanded by remember { mutableStateOf(false) }
        MyButton(
            onClick = { upgradesExpanded = !upgradesExpanded },
            text = "Upgrades",
            trailingIcon = Icons.Default.KeyboardArrowDown
        )
        if (upgradesExpanded) {
            crew.upgrades.forEach { upgrade ->
                CrewUpgradeItem(
                    upgrade = upgrade,
                    modifier = Modifier
                        .padding(4.dp),
                    enableDelete = false,
                    onClick = { doUpgradePopUp(it) },
                    displayDeleteUpgradeDialog = {} //displayDeleteDialog
                )
            }//end LazyColumn
        }//end contacts expanded
    }
}
