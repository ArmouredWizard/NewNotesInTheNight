package uk.co.maddwarf.notesinthenight.ui.screens.scoundrel

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.persistentListOf
import uk.co.maddwarf.notesinthenight.model.Contact
import uk.co.maddwarf.notesinthenight.model.ContactWithRating
import uk.co.maddwarf.notesinthenight.model.Crew
import uk.co.maddwarf.notesinthenight.model.NameDescrRatingResult
import uk.co.maddwarf.notesinthenight.model.Scoundrel
import uk.co.maddwarf.notesinthenight.model.SpecialAbility
import uk.co.maddwarf.notesinthenight.model.toContactWithRating
import uk.co.maddwarf.notesinthenight.ui.composables.ActionBlock
import uk.co.maddwarf.notesinthenight.ui.composables.CoinBlock
import uk.co.maddwarf.notesinthenight.ui.composables.DeleteConfirmationDialog
import uk.co.maddwarf.notesinthenight.ui.composables.MyButton
import uk.co.maddwarf.notesinthenight.ui.composables.NameAndDescrAndRatingEntryDialog
import uk.co.maddwarf.notesinthenight.ui.composables.NameAndDescrEntryDialog
import uk.co.maddwarf.notesinthenight.ui.composables.TextEntryRowWithInfoIcon
import uk.co.maddwarf.notesinthenight.ui.composables.TextEntryWithSpinner
import uk.co.maddwarf.notesinthenight.ui.composables.TitleBlock
import uk.co.maddwarf.notesinthenight.ui.composables.XpBlock
import uk.co.maddwarf.notesinthenight.ui.composables.ability.AbilityItem
import uk.co.maddwarf.notesinthenight.ui.composables.ability.MyAbilitySpinner
import uk.co.maddwarf.notesinthenight.ui.composables.contact.ContactWithRatingItem
import uk.co.maddwarf.notesinthenight.ui.composables.contact.MyContactSpinner
import uk.co.maddwarf.notesinthenight.ui.composables.crew.MyCrewSpinner

@Composable
fun ScoundrelInputForm(
    scoundrelDetails: Scoundrel,
    modifier: Modifier = Modifier,
    onValueChange: (Scoundrel) -> Unit = {},
    enabled: Boolean = true,
    playbookList: List<String>,

    everyCrewList: List<Crew>,
    heritageList: List<String>,
    backgroundList: List<String>,
    everyAbilityList: List<SpecialAbility>,
    everyContactList: List<Contact>,
) {

    fun circleClicker(aString: String, aIndex: Int) {
        when (aString) {
            "Hunt" -> onValueChange(scoundrelDetails.copy(hunt =aIndex))
            "Study" -> onValueChange(scoundrelDetails.copy(study =aIndex))
            "Survey" -> onValueChange(scoundrelDetails.copy(survey =aIndex))
            "Tinker" -> onValueChange(scoundrelDetails.copy(tinker =aIndex))
            "Finesse" -> onValueChange(scoundrelDetails.copy(finesse =aIndex))
            "Prowl" -> onValueChange(scoundrelDetails.copy(prowl =aIndex))
            "Skirmish" -> onValueChange(scoundrelDetails.copy(skirmish =aIndex))
            "Wreck" -> onValueChange(scoundrelDetails.copy(wreck =aIndex))
            "Attune" -> onValueChange(scoundrelDetails.copy(attune =aIndex))
            "Command" -> onValueChange(scoundrelDetails.copy(command =aIndex))
            "Consort" -> onValueChange(scoundrelDetails.copy(consort =aIndex))
            "Sway" -> onValueChange(scoundrelDetails.copy(sway =aIndex))

            "Coin" -> onValueChange(scoundrelDetails.copy(coin = aIndex))

            "Insight" -> onValueChange(scoundrelDetails.copy(insightXp = aIndex))
            "Prowess" -> onValueChange(scoundrelDetails.copy(prowessXp = aIndex))
            "Resolve" -> onValueChange(scoundrelDetails.copy(resolveXp = aIndex))
            "Playbook" -> onValueChange(scoundrelDetails.copy(playbookXp = aIndex))

        }
    }
    Column(
        modifier = modifier
            .padding(start = 10.dp, end = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextEntryRowWithInfoIcon(
            data = scoundrelDetails.name,
            onValueChange = {
                onValueChange(scoundrelDetails.copy(name = it))
            },
            label = "Name",
            infoText = "Enter your Scoundrel's Name.\n" +
                    "This can be a Nickname, Moniker, Handle or anything that identifies them."
        )

        CrewBlock(
            onValueChange = onValueChange,
            scoundrelDetails = scoundrelDetails,
            everyCrewList = everyCrewList,
        )

        TextEntryWithSpinner(
            textValue = scoundrelDetails.playbook,
            label = "Playbook",
            infoText = "Choose your Scoundrel's Playbook.\n" +
                    "This defines their XP Triggers, and initial access to Special Abilities.",
            onValueChange = {
                onValueChange(scoundrelDetails.copy(playbook = it))
            },
            itemList = playbookList
        )

        TextEntryWithSpinner(
            textValue = scoundrelDetails.heritage,
            label = "Heritage",
            infoText = "Choose your Scoundrel's Heritage.\n" +
                    "Where does your Scoundrel hail from? Are they local, or from far-afield?",
            onValueChange = {
                onValueChange(scoundrelDetails.copy(heritage = it))
            },
            itemList = heritageList
        )

        TextEntryWithSpinner(
            textValue = scoundrelDetails.background,
            label = "Background",
            infoText = "Choose your Scoundrel's Background.\n" +
                    "What was their upbringing like? What was their major influence?",
            onValueChange = {
                onValueChange(scoundrelDetails.copy(background = it))
            },
            itemList = backgroundList
        )

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
                        Pair("Hunt", scoundrelDetails.hunt),
                        Pair("Study", scoundrelDetails.study),
                        Pair("Survey", scoundrelDetails.survey),
                        Pair("Tinker", scoundrelDetails.tinker)
                    ),
                    onRankClicked = { aString: String, aIndex: Int ->
                        circleClicker(aString, aIndex)
                    }
                )//end ActionBlock
                ActionBlock(
                    attribute = "Prowess",
                    actions = persistentListOf(
                        Pair("Finesse", scoundrelDetails.finesse),
                        Pair("Prowl", scoundrelDetails.prowl),
                        Pair("Skirmish", scoundrelDetails.skirmish),
                        Pair("Wreck", scoundrelDetails.wreck)
                    ),
                    onRankClicked = { aString: String, aIndex: Int ->
                        circleClicker(aString, aIndex)
                    }
                )//end ActionBlock
                ActionBlock(
                    attribute = "Resolve",
                    actions = persistentListOf(
                        Pair("Attune", scoundrelDetails.attune),
                        Pair("Command", scoundrelDetails.command),
                        Pair("Consort", scoundrelDetails.consort),
                        Pair("Sway", scoundrelDetails.sway)
                    ),
                    onRankClicked = { aString: String, aIndex: Int ->
                        circleClicker(aString, aIndex)
                    }
                )//end ActionBlock
            }
        }//end Action Blocks box

        CoinBlock(
            scoundrelDetails = scoundrelDetails,
            onRankClicked = { aString: String, aIndex: Int ->
                circleClicker(aString, aIndex)
            }
        )

        XpBlock(
            xpTracks = listOf(
                Pair("Insight", scoundrelDetails.insightXp),
                Pair("Prowess", scoundrelDetails.prowessXp),
                Pair("Resolve", scoundrelDetails.resolveXp),
                //    Pair("Playbook", scoundrelDetails.playbookXp)
            ),
            onRankClicked = { aString: String, aIndex: Int ->
                circleClicker(aString, aIndex)
            },
            xpTotal = 6
        )
        XpBlock(
            xpTracks = listOf(Pair("Playbook", scoundrelDetails.playbookXp)),
            onRankClicked = { aString: String, aIndex: Int ->
                circleClicker(aString, aIndex)
            },
            xpTotal = 8,
        )

        SpecialAbilityBlock(
            onValueChange = onValueChange,
            scoundrelDetails = scoundrelDetails,
            everyAbilityList = everyAbilityList,
        )

        Spacer(modifier = Modifier.height(5.dp))

        ContactsBlock(
            onValueChange = onValueChange,
            scoundrelDetails = scoundrelDetails,
            everyContactList = everyContactList,
        )

    }//end Column
}//end ScoundrelInputForm

@Composable
fun CrewBlock(
    onValueChange: (Scoundrel) -> Unit,
    scoundrelDetails: Scoundrel,
    everyCrewList: List<Crew>,
) {

    var newCrew by remember { mutableStateOf(Crew()) }
    var showCrewDialog by remember { mutableStateOf(false) }

    var everyCrewNameList: MutableList<Crew> = mutableListOf()
    everyCrewList.forEach {
        everyCrewNameList.add(it)
    }
    everyCrewNameList = everyCrewNameList.distinct().toMutableList()

    fun crewDialogClick() {
        showCrewDialog = !showCrewDialog
    }

    fun onCrewChange(crewName: String) {
        newCrew = newCrew.copy(crewName = crewName)
    }

    fun onTypeChange(type: String) {
        newCrew = newCrew.copy(crewType = type)
    }

    fun doNewCrewFromPair(info: Pair<String, String>) {
        showCrewDialog = false
        onValueChange(
            scoundrelDetails.copy(
                crew = Crew(
                    crewName = info.first,
                    crewType = info.second
                )
            )
        )
        newCrew = Crew()
    }

    var showRemoveCrewDialog by remember { mutableStateOf(false) }
    var chosenCrew by remember { mutableStateOf(Crew()) }

    fun crewDeleteDialogClick(crew: Crew) {
        chosenCrew = crew
        showRemoveCrewDialog = !showRemoveCrewDialog
    }

    fun doRemoveCrew() {
        showRemoveCrewDialog = false
        onValueChange(scoundrelDetails.copy(crew = null))
    }

    if (showRemoveCrewDialog) {
        DeleteConfirmationDialog(
            title = "Crew",
            name = chosenCrew.crewName,
            onAccept = { doRemoveCrew() },
            onDismiss = { showRemoveCrewDialog = false },
            deleteType = "Remove"
        )
    }

    val crew = scoundrelDetails.crew

    TitleBlock(title = "", text = "Crew")
    if (crew != null) {
        CrewItem(
            crew = crew,
            displayDeleteCrewDialog = { crewDeleteDialogClick(it) },
            onClick = {}
        )
    }

    var crewExpanded by remember { mutableStateOf(false) }
    var chosenExistingCrew by remember { mutableStateOf(Crew()) }
    fun crewChooser(crew: Crew) {
        crewExpanded = false
        chosenExistingCrew = crew
        onValueChange(scoundrelDetails.copy(crew = crew))
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            MyButton(
                onClick = { crewDialogClick() },
                text = "New Crew",
                leadingIcon = Icons.Default.Add
            )
        }
        Spacer(modifier = Modifier.width(5.dp))
        Row(
            modifier = Modifier.weight(1f)
        ) {
            MyCrewSpinner(
                expanded = crewExpanded,
                onClick = { crewExpanded = !crewExpanded },
                list = everyCrewList,
                chooser = ::crewChooser,
                report = "Choose Crew"
            )
        }
    }
    if (showCrewDialog) {
        NameAndDescrEntryDialog(
            name = newCrew.crewName,
            description = newCrew.crewType,
            onDismiss = { showCrewDialog = !showCrewDialog;newCrew = Crew() },
            onAccept = { doNewCrewFromPair(it) },
            onNameChange = { onCrewChange(it) },
            onDescriptionChange = { onTypeChange(it) },
            title = "New Crew",
            nameLabel = "Crew name",
            nameHint = "Name your Crew",
            descriptionLabel = "Crew Type",
            descriptionHint = "Enter type of Crew"
        )
    }
}//end Crew Block

@Composable
fun CrewItem(
    crew: Crew,
    modifier: Modifier = Modifier,
    enableDelete: Boolean = true,
    displayDeleteCrewDialog: (Crew) -> Unit,
    onClick: (Crew) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(.9f),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .clickable { onClick(crew) }
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = crew.crewName,
                    fontSize = 20.sp
                )
                if (enableDelete) {
                    Icon(
                        imageVector = Icons.TwoTone.Delete,
                        contentDescription = "Delete",
                        Modifier
                            .clickable { displayDeleteCrewDialog(crew) }
                            .align(Alignment.CenterVertically)
                            .size(25.dp)
                    )
                }
            }
            Row {
                Text(text = "Type: ${crew.crewType}", style = MaterialTheme.typography.bodySmall)
            }
        }//end column
    }//end card
}//end CrewItem

@Composable
fun SpecialAbilityBlock(
    onValueChange: (Scoundrel) -> Unit,
    scoundrelDetails: Scoundrel,
    everyAbilityList: List<SpecialAbility>,
) {
    var newAbility by remember { mutableStateOf(SpecialAbility()) }
    var showAbilityDialog by remember { mutableStateOf(false) }

    var everyAbilityNameList: MutableList<SpecialAbility> = mutableListOf()
    everyAbilityList.forEach {
        everyAbilityNameList.add(it)
    }
    everyAbilityNameList = everyAbilityNameList.distinct().toMutableList()

    fun abilityDialogClick() {
        showAbilityDialog = !showAbilityDialog
    }

    fun onAbilityChange(abilityName: String) {
        newAbility = newAbility.copy(abilityName = abilityName)
    }

    fun onDescriptionChange(abilityDescription: String) {
        newAbility = newAbility.copy(abilityDescription = abilityDescription)
    }

    fun doNewAbilityFromPair(info: Pair<String, String>) {
        showAbilityDialog = false
        onValueChange(
            scoundrelDetails.copy(
                specialAbilities = scoundrelDetails.specialAbilities + SpecialAbility(
                    abilityName = info.first,
                    abilityDescription = info.second
                )
            )
        )
        newAbility = SpecialAbility()
    }

    var showRemoveAbilityDialog by remember { mutableStateOf(false) }
    var chosenAbility by remember { mutableStateOf(SpecialAbility()) }

    fun abilityDeleteDialogClick(ability: SpecialAbility) {
        chosenAbility = ability
        showRemoveAbilityDialog = !showRemoveAbilityDialog
    }

    fun doRemoveAbility(ability: SpecialAbility) {
        showRemoveAbilityDialog = false
        val newAbilityList = mutableListOf<SpecialAbility>()
        scoundrelDetails.specialAbilities.forEach {
            if (it != ability) newAbilityList.add(it)
        }
        onValueChange(
            scoundrelDetails.copy(
                specialAbilities = newAbilityList
            )
        )
    }

    if (showRemoveAbilityDialog) {
        DeleteConfirmationDialog(
            title = "Special Ability",
            name = chosenAbility.abilityName,
            onAccept = { doRemoveAbility(chosenAbility) },
            onDismiss = { showRemoveAbilityDialog = false },
            deleteType = "Remove"
        )
    }

    val abilitiesList = scoundrelDetails.specialAbilities

    TitleBlock(title = "", text = "Special Abilities")
    abilitiesList.forEach { it ->
        AbilityItem(
            ability = it,
            displayDeleteAbilityDialog = { abilityDeleteDialogClick(it) },
        )
    }

    var abilityExpanded by remember { mutableStateOf(false) }
    var chosenExistingAbility by remember { mutableStateOf(SpecialAbility()) }
    fun abilityChooser(ability: SpecialAbility) {
        abilityExpanded = false
        chosenExistingAbility = ability
        onValueChange(scoundrelDetails.copy(specialAbilities = scoundrelDetails.specialAbilities + ability))
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            MyButton(
                onClick = { abilityDialogClick() },
                text = "New Ability",
                leadingIcon = Icons.Default.Add
            )
        }
        Spacer(modifier = Modifier.width(5.dp))
        Row(
            modifier = Modifier.weight(1f)
        ) {
            MyAbilitySpinner(
                expanded = abilityExpanded,
                onClick = { abilityExpanded = !abilityExpanded },
                list = everyAbilityList,
                chooser = ::abilityChooser,
                report = "Chose Ability"
            )
        }
    }
    if (showAbilityDialog) {
        NameAndDescrEntryDialog(
            name = newAbility.abilityName,
            description = newAbility.abilityDescription,
            onDismiss = { showAbilityDialog = !showAbilityDialog },
            onAccept = { doNewAbilityFromPair(it) },
            onNameChange = { onAbilityChange(it) },
            onDescriptionChange = { onDescriptionChange(it) },
            title = "New Special Ability",
            nameLabel = "Special Ability Name",
            nameHint = "Name the Special Ability",
            descriptionLabel = "Special Ability Description",
            descriptionHint = "A description of the Special Ability"
        )
    }

}//end Abilities Block

// begin Contacts block

@Composable
fun ContactsBlock(
    onValueChange: (Scoundrel) -> Unit,
    scoundrelDetails: Scoundrel,
    everyContactList: List<Contact>,
) {
    var newContact by remember { mutableStateOf(ContactWithRating()) }
    var showContactDialog by remember { mutableStateOf(false) }

    var everyContactNameList: MutableList<Contact> = mutableListOf()
    everyContactList.forEach {
        everyContactNameList.add(it)
    }
    everyContactNameList = everyContactNameList.distinct().toMutableList()

    fun contactDialogClick() {
        showContactDialog = !showContactDialog
    }

    fun onContactChange(contactName: String) {
        newContact = newContact.copy(contactName = contactName)
    }

    fun onDescriptionChange(description: String) {
        newContact = newContact.copy(contactDescription = description)
    }

    fun onRatingChange(rating:Int){
        newContact = newContact.copy(rating = rating)
    }

    fun doNewContactFromResult(info: NameDescrRatingResult) {
        Log.d("INFO", info.toString())
        showContactDialog = false
        onValueChange(
            scoundrelDetails.copy(
                contacts = scoundrelDetails.contacts + ContactWithRating(
                    connectionId = scoundrelDetails.scoundrelId,
                    contactName = info.name,
                    contactDescription = info.descr,
                    rating = info.rating
                )
            )
        )
        newContact = ContactWithRating()
    }

    var showRemoveContactDialog by remember { mutableStateOf(false) }
    var chosenContact by remember { mutableStateOf(ContactWithRating()) }

    fun contactDeleteDialogClick(contact: ContactWithRating) {
        chosenContact = contact
        showRemoveContactDialog = !showRemoveContactDialog
    }

    fun doRemoveContact(contact: ContactWithRating) {
        showRemoveContactDialog = false
        val newContactList = mutableListOf<ContactWithRating>()
        scoundrelDetails.contacts.forEach {
            if (it != contact) newContactList.add(it)
        }
        onValueChange(scoundrelDetails.copy(contacts = newContactList))
    }

    if (showRemoveContactDialog) {
        DeleteConfirmationDialog(
            title = "Contact",
            name = chosenContact.contactName,
            onAccept = { doRemoveContact(chosenContact) },
            onDismiss = { showRemoveContactDialog = false },
            deleteType = "Remove"
        )
    }

    val contactList = scoundrelDetails.contacts

    TitleBlock(title = "", text = "Contacts")
    contactList.forEach { it ->
        fun editRatingClick(rating:Int){
            it.rating = rating
            showRemoveContactDialog = true
            showRemoveContactDialog = false //hack to force recompose
        }
        ContactWithRatingItem(
            contact = it,
            displayDeleteContactDialog = { contactDeleteDialogClick(it) },
            onRatingClick = {editRatingClick(it)},
            onClick = {}
        )
    }

    var contactExpanded by remember { mutableStateOf(false) }
    var chosenExistingContact by remember { mutableStateOf(ContactWithRating()) }
    fun contactChooser(contact: Contact) {
        contactExpanded = false
        chosenExistingContact = contact.toContactWithRating()
        onValueChange(scoundrelDetails.copy(contacts = scoundrelDetails.contacts + chosenExistingContact))
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            MyButton(
                onClick = { contactDialogClick() },
                text = "New Contact",
                leadingIcon = Icons.Default.Add
            )
        }
        Spacer(modifier = Modifier.width(5.dp))
        Row(
            modifier = Modifier.weight(1f)
        ) {
            MyContactSpinner(
                expanded = contactExpanded,
                onClick = { contactExpanded = !contactExpanded },
                list = everyContactList,
                chooser = ::contactChooser,
                report = "Chose Contact"
            )
        }
    }
    if (showContactDialog) {
        NameAndDescrAndRatingEntryDialog(
            name = newContact.contactName,
            description = newContact.contactDescription,
            rating = newContact.rating,
            onDismiss = { showContactDialog = !showContactDialog },
            onAccept = { doNewContactFromResult(it) },
            onNameChange = { onContactChange(it) },
            onDescriptionChange = { onDescriptionChange(it) },
            onRatingChange = {onRatingChange(it)},
            title = "New Contact",
            nameLabel = "Contact name",
            nameHint = "Name this Contact",
            descriptionLabel = "Contact Description",
            descriptionHint = "Enter a description for the Contact"
        )
    }

}//end Contact Block



