package uk.co.maddwarf.notesinthenight.ui.screens.scoundrel

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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.collections.immutable.persistentListOf
import uk.co.maddwarf.notesinthenight.NotesInTheNightTopAppBar
import uk.co.maddwarf.notesinthenight.R
import uk.co.maddwarf.notesinthenight.model.Contact
import uk.co.maddwarf.notesinthenight.model.ContactWithRating
import uk.co.maddwarf.notesinthenight.model.Scoundrel
import uk.co.maddwarf.notesinthenight.model.SpecialAbility
import uk.co.maddwarf.notesinthenight.navigation.NavigationDestination
import uk.co.maddwarf.notesinthenight.ui.composables.ActionBlock
import uk.co.maddwarf.notesinthenight.ui.composables.CoinBlock
import uk.co.maddwarf.notesinthenight.ui.composables.InfoPopUp
import uk.co.maddwarf.notesinthenight.ui.composables.InfoPopUpVariable
import uk.co.maddwarf.notesinthenight.ui.composables.MyButton
import uk.co.maddwarf.notesinthenight.ui.composables.TraitText
import uk.co.maddwarf.notesinthenight.ui.composables.XpBlock
import uk.co.maddwarf.notesinthenight.ui.composables.ability.AbilityItem
import uk.co.maddwarf.notesinthenight.ui.composables.contact.ContactItem
import uk.co.maddwarf.notesinthenight.ui.composables.contact.ContactWithRatingItem
import uk.co.maddwarf.notesinthenight.ui.composables.crew.CrewInfoDialog

object ScoundrelDetailsDestination : NavigationDestination {
    override val route = "scoundrel_details"
    override val titleRes: Int = R.string.scoundrel_detail_title

    const val itemIdArg = "scoundrel_id"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoundrelDetailsScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToScoundrelEdit: (Int) -> Unit,
    navigateToCrewDetails: (Int) -> Unit,
    viewModel: ScoundrelDetailsViewModel = hiltViewModel()
) {
    val uiState = viewModel.detailsUiState.collectAsState()

    val contactsList:List<ContactWithRating> by viewModel.contactsList.collectAsState(initial = listOf())

    Scaffold(
        topBar = {
            NotesInTheNightTopAppBar(
                title = uiState.value.scoundrelDetails.name,
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
                    Log.d("ID", uiState.value.scoundrelDetails.scoundrelId.toString())
                    navigateToScoundrelEdit(uiState.value.scoundrelDetails.scoundrelId)
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
        ScoundrelDetailsBody(
            detailsUiState = uiState.value,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            navigateToCrewDetails = navigateToCrewDetails,
            contactsList = contactsList
        )
    }//end scaffold
}//end Scoundrel Details Screen

@Composable
fun ScoundrelDetailsBody(
    detailsUiState: ScoundrelDetailsUiState,
    modifier: Modifier,
    navigateToCrewDetails: (Int) -> Unit,
    contactsList:List<ContactWithRating>
) {

    Log.d("SCOUNDREL DETAILS", detailsUiState.scoundrelDetails.toString())
    Log.d("CONTACTS DETAILS", contactsList.toString())
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .paint(painterResource(id = R.drawable.cobbles), contentScale = ContentScale.FillBounds)
    ) {
        //show details here
        Column(
            modifier = modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ScoundrelDetails(
                scoundrel = detailsUiState.scoundrelDetails,
                contactsList = contactsList,
                modifier = Modifier.fillMaxWidth(),
                navigateToCrewDetails = navigateToCrewDetails,
            )
        }
    }
}//end detailsBody

@Composable
fun ScoundrelDetails(
    scoundrel: Scoundrel,
    contactsList: List<ContactWithRating>,
    modifier: Modifier,
    navigateToCrewDetails: (Int) -> Unit,
) {

    var showCrewDialog by remember { mutableStateOf(false) }
    fun onCrewClick() {
        showCrewDialog = !showCrewDialog
    }
    if (showCrewDialog && scoundrel.crew!=null) {
        CrewInfoDialog(
            onDismiss = { showCrewDialog = false },
            onAccept = { navigateToCrewDetails(scoundrel.crew!!.crewId) },
            crew = scoundrel.crew!!
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.fillMaxWidth(.45f)) {
                TraitText(title = "Playbook: ", text = scoundrel.playbook)
            }
            Row(modifier = Modifier.fillMaxWidth(.9f)) {
                TraitText(
                    title = "Crew: ",
                    text = if (scoundrel.crew!=null) scoundrel.crew!!.crewName else "No Crew",
                    onClick = { onCrewClick() }
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

                TraitText(title = "Heritage: ", text = scoundrel.heritage)
            }
            Row(modifier = Modifier.fillMaxWidth(.9f)) {

                TraitText(title = "Background: ", text = scoundrel.background)
            }
        }

        var showAttributesBlock by remember { mutableStateOf(false) }
        MyButton(
            onClick = { showAttributesBlock = !showAttributesBlock },
            text = "Attributes",
            trailingIcon = if (showAttributesBlock) {
                Icons.Default.KeyboardArrowUp
            } else {
                Icons.Default.KeyboardArrowDown
            }
        )

        if (showAttributesBlock) {
            Box(
                modifier = modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    //.fillMaxWidth(.7f)
                    .wrapContentWidth()
                    .background(color = Color.DarkGray)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ActionBlock(
                        attribute = "Insight",
                        actions = persistentListOf(
                            Pair("Hunt", scoundrel.hunt),
                            Pair("Study", scoundrel.study),
                            Pair("Survey", scoundrel.survey),
                            Pair("Tinker", scoundrel.tinker)
                        ),
                        rollable = true
                    )
                    ActionBlock(
                        attribute = "Prowess",
                        actions = persistentListOf(
                            Pair("Finesse", scoundrel.finesse),
                            Pair("Prowl", scoundrel.prowl),
                            Pair("Skirmish", scoundrel.skirmish),
                            Pair("Wreck", scoundrel.wreck)
                        ),
                        rollable = true
                    )
                    ActionBlock(
                        attribute = "Resolve",
                        actions = persistentListOf(
                            Pair("Attune", scoundrel.attune),
                            Pair("Command", scoundrel.command),
                            Pair("Consort", scoundrel.consort),
                            Pair("Sway", scoundrel.sway)
                        ),
                        rollable = true
                    )
                }//end Column
            }//end Box


            CoinBlock(
                scoundrelDetails = scoundrel,
            )


        }//end IF ATTRIBUTES
        Spacer(Modifier.height(10.dp))

        var showAbilityPopUp by remember { mutableStateOf(false) }
        var chosenAbility by remember { mutableStateOf(SpecialAbility()) }
        fun doAbilityPopUp(ability: SpecialAbility) {
            chosenAbility = ability
            showAbilityPopUp = true
        }

        if (showAbilityPopUp) {
            InfoPopUp(
                title = "Special Ability",
                firstTextTitle = "Ability Name",
                firstText = chosenAbility.abilityName,
                secondTextTitle = "Ability Description",
                secondText = chosenAbility.abilityDescription,
                onDismiss = { showAbilityPopUp = false }
            )
        }

        var showAbilitiesBlock by remember { mutableStateOf(false) }
        MyButton(
            onClick = { showAbilitiesBlock = !showAbilitiesBlock },
            text = "Special Abilities",
            trailingIcon = if (showAbilitiesBlock) {
                Icons.Default.KeyboardArrowUp
            } else {
                Icons.Default.KeyboardArrowDown
            }
        )
        Spacer(modifier = Modifier.height(5.dp))
        if (showAbilitiesBlock) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (scoundrel.specialAbilities.isNotEmpty()) {
                    scoundrel.specialAbilities.forEach { ability ->
                        if (ability.abilityName != "") {
                            Spacer(modifier = Modifier.height(5.dp))
                            AbilityItem(
                                ability = ability,
                                enableDelete = false,
                                displayDeleteAbilityDialog = {},
                                onClick = { doAbilityPopUp(it) }
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                } else {
                    Text(text = "No Abilities")
                }
            }
        }//end IF Abilities block

        var showContactPopUp by remember { mutableStateOf(false) }
        var chosenContact by remember { mutableStateOf(ContactWithRating()) }
        fun doContactPopUp(contact: ContactWithRating) {
            chosenContact = contact
            showContactPopUp = true
        }

        if (showContactPopUp) {
            val ratingText = when (chosenContact.rating){
                -1 -> "Poor"
                0 -> "Neutral"
                1 -> "Good"
                else -> "undefined"
            }
            InfoPopUpVariable(
                title = "Contact",

                entries = listOf(
                    Pair("Contact Name", chosenContact.contactName),
                    Pair("Contact Description", chosenContact.contactDescription),
                    Pair("Contact Rating", ratingText)
                ),

    /*            firstTextTitle = "Contact Name",
                firstText = chosenContact.name,
                secondTextTitle = "Contact Description",
                secondText = chosenContact.description,*/
                onDismiss = { showContactPopUp = false }
            )
        }

        var showContactsBlock by remember { mutableStateOf(false) }

        //val contactList = scoundrel.contacts
        val contactList = contactsList

        MyButton(
            onClick = { showContactsBlock = !showContactsBlock },
            text = "Contacts",
            trailingIcon = if (showContactsBlock) {
                Icons.Default.KeyboardArrowUp
            } else {
                Icons.Default.KeyboardArrowDown
            }
        )
        Spacer(modifier = Modifier.height(5.dp))
        if (showContactsBlock) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (contactList.isNotEmpty()) {
                    contactList.forEach { it ->
                        Spacer(modifier = Modifier.height(5.dp))
                        ContactWithRatingItem(
                            contact = it,
                            enableDelete = false,
                            onClick = { doContactPopUp(it) },
                            displayDeleteContactDialog = {},
                            onRatingClick = {},//todo
                            changeRating = false
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                } else {
                    Text(text = "No Contacts")
                }
            }
        }//end IF Contacts block

          var showXpBlock by remember { mutableStateOf(false) }

       // val contactList = scoundrel.contacts
        MyButton(
            onClick = { showXpBlock = !showXpBlock },
            text = "XP Tracks",
            trailingIcon = if (showXpBlock) {
                Icons.Default.KeyboardArrowUp
            } else {
                Icons.Default.KeyboardArrowDown
            }
        )
        Spacer(modifier = Modifier.height(5.dp))
        if (showXpBlock) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                XpBlock(
                    xpTracks = listOf(
                        Pair("Insight", scoundrel.insightXp),
                        Pair("Prowess", scoundrel.prowessXp),
                        Pair("Resolve", scoundrel.resolveXp),
                        //    Pair("Playbook", scoundrelDetails.playbookXp)
                    ),

                    xpTotal = 6
                )
                XpBlock(
                    xpTracks = listOf(Pair("Playbook", scoundrel.playbookXp)),

                    xpTotal = 8,
                )
            }
        }//end IF Contacts block



    }//end Column

}//end Scoundrel Details