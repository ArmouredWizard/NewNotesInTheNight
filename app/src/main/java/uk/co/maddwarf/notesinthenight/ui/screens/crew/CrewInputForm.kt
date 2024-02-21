package uk.co.maddwarf.notesinthenight.ui.screens.crew

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uk.co.maddwarf.notesinthenight.R
import uk.co.maddwarf.notesinthenight.model.Contact
import uk.co.maddwarf.notesinthenight.model.Crew
import uk.co.maddwarf.notesinthenight.model.CrewAbility
import uk.co.maddwarf.notesinthenight.model.CrewUpgrade
import uk.co.maddwarf.notesinthenight.ui.composables.DeleteConfirmationDialog
import uk.co.maddwarf.notesinthenight.ui.composables.MyButton
import uk.co.maddwarf.notesinthenight.ui.composables.NameAndDescrEntryDialog
import uk.co.maddwarf.notesinthenight.ui.composables.TextEntryRowWithInfoIcon
import uk.co.maddwarf.notesinthenight.ui.composables.TextEntryWithSpinner
import uk.co.maddwarf.notesinthenight.ui.composables.TitleBlock
import uk.co.maddwarf.notesinthenight.ui.composables.TraitDots
import uk.co.maddwarf.notesinthenight.ui.composables.contact.ContactItem
import uk.co.maddwarf.notesinthenight.ui.composables.contact.MyContactSpinner
import uk.co.maddwarf.notesinthenight.ui.composables.crew.MyCrewAbilitySpinner
import uk.co.maddwarf.notesinthenight.ui.composables.crew.MyCrewUpgradeSpinner

@Composable
fun CrewInputForm(
    crewDetails: Crew,
    crewTypeList: List<String>,
    crewReputationList: List<String>,
    everyAbilityList: List<CrewAbility>,
    everyContactList: List<Contact>,
    onValueChange: (Crew) -> Unit,
    everyUpgradeList: List<CrewUpgrade>,
) {

    val characterLimit = 100
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextEntryRowWithInfoIcon(
            data = crewDetails.crewName,
            onValueChange = { onValueChange(crewDetails.copy(crewName = it)) },
            label = "Crew Name",
            infoText = "Choose a name for your Crew.\n" +
                    "This will help you to find and identify it."
        )
        TextEntryWithSpinner(
            textValue = crewDetails.crewType,
            label = "Type",
            infoText = "Choose the Type of Crew.\n" +
                    "This describes the main activities your Crew is known for",
            onValueChange = {
                if (it.length <= characterLimit) onValueChange(
                    crewDetails.copy(
                        crewType = it
                    )
                )
            },
            itemList = crewTypeList
        )

        TextEntryWithSpinner(
            textValue = crewDetails.reputation,
            label = "Reputation",
            infoText = "Choose the type of Reputation this Crew has.\n" + "",
            itemList = crewReputationList,
            onValueChange = { onValueChange(crewDetails.copy(reputation = it)) },
        )

        TextEntryRowWithInfoIcon(
            data = crewDetails.hq,
            onValueChange = { onValueChange(crewDetails.copy(hq = it)) },
            label = "HQ",
            infoText = "Choose a Location for your Crew's Headquarters.\n" +
                    ""
        )
        TextEntryRowWithInfoIcon(
            data = crewDetails.huntingGrounds,
            onValueChange = { onValueChange(crewDetails.copy(huntingGrounds = it)) },
            label = "Hunting Grounds",
            infoText = "Choose a Hunting Ground for your Crew.\n" +
                    "This is where the Crew touts for business, and gains most of their work.\n" +
                    "It does not have to be in the same area as their HQ"
        )
        val strong = if (crewDetails.holdIsStrong) {
            "Strong"
        } else {
            "Weak"
        }
        TraitDots(
            traitName = "Rep",
            traitValue = crewDetails.rep.toInt(),
            maxValue = 12,
            onDotClicked = { onValueChange(crewDetails.copy(rep = it)) },
            infoText = stringResource(R.string.reputation_info)  //todo expand on this
        )
        TraitDots(
            traitName = "Heat",
            traitValue = crewDetails.heat,
            maxValue = 9,
            onDotClicked = { onValueChange(crewDetails.copy(heat = it)) },
            infoText = stringResource(R.string.heat_info) //todo expand
        )
        TraitDots(
            traitName = "Tier",
            traitValue = crewDetails.tier,
            maxValue = 4,
            onDotClicked = { onValueChange(crewDetails.copy(tier = it)) },
            infoText = stringResource(R.string.tier_info) //todo rewrite
        )

        var switchOn by remember {
            mutableStateOf(false)
        }
//Hold Switch
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Switch(
                checked = switchOn,
                onCheckedChange = { switchOn_ ->
                    switchOn = switchOn_
                    onValueChange(crewDetails.copy(holdIsStrong = (switchOn)))
                    Log.d("SWITCHING", "switchOn $switchOn, Strong: $strong")
                }
            )
            Text(text = if (switchOn) "Strong" else "Weak")
        }
        //end Hold Switch

        CrewAbilityBlock(
            onValueChange = onValueChange,
            crewDetails = crewDetails,
            everyAbilityList = everyAbilityList,
        )

        CrewContactsBlock(
            onValueChange = onValueChange,
            crewDetails = crewDetails,
            everyContactList = everyContactList,
        )

        CrewUpgradesBlock(
            onValueChange = onValueChange,
            crewDetails = crewDetails,
            everyUpgradeList = everyUpgradeList,
        )

    }//end column
}//end CrewEntryForm

@Composable
fun CrewAbilityBlock(
    onValueChange: (Crew) -> Unit,
    crewDetails: Crew,
    everyAbilityList: List<CrewAbility>,
) {
    var newAbility by remember { mutableStateOf(CrewAbility()) }
    var showAbilityDialog by remember { mutableStateOf(false) }

    var everyAbilityNameList: MutableList<CrewAbility> = mutableListOf()
    everyAbilityList.forEach {
        everyAbilityNameList.add(it)
    }
    everyAbilityNameList = everyAbilityNameList.distinct().toMutableList()

    fun abilityDialogClick() {
        showAbilityDialog = !showAbilityDialog
    }

    fun onAbilityChange(abilityName: String) {
        newAbility = newAbility.copy(crewAbilityName = abilityName)
    }

    fun onDescriptionChange(abilityDescription: String) {
        newAbility = newAbility.copy(crewAbilityDescription = abilityDescription)
    }

    fun doNewAbilityFromPair(info: Pair<String, String>) {
        showAbilityDialog = false
        onValueChange(
            crewDetails.copy(
                crewAbilities = crewDetails.crewAbilities + CrewAbility(
                    crewAbilityName = info.first,
                    crewAbilityDescription = info.second
                )
            )
        )
        newAbility = CrewAbility()
    }

    var showRemoveAbilityDialog by remember { mutableStateOf(false) }
    var chosenCrewAbility by remember { mutableStateOf(CrewAbility()) }

    fun abilityDeleteDialogClick(ability: CrewAbility) {
        chosenCrewAbility = ability
        showRemoveAbilityDialog = !showRemoveAbilityDialog
    }

    fun doRemoveAbility(ability: CrewAbility) {
        showRemoveAbilityDialog = false
        val newAbilityList = mutableListOf<CrewAbility>()
        crewDetails.crewAbilities.forEach {
            if (it != ability) newAbilityList.add(it)
        }
        onValueChange(
            crewDetails.copy(
                crewAbilities = newAbilityList
            )
        )
    }

    if (showRemoveAbilityDialog) {
        DeleteConfirmationDialog(
            title = "Crew Ability",
            name = chosenCrewAbility.crewAbilityName,
            onAccept = { doRemoveAbility(chosenCrewAbility) },
            onDismiss = { showRemoveAbilityDialog = false },
            deleteType = "Remove"
        )
    }

    val abilitiesList = crewDetails.crewAbilities

    TitleBlock(title = "", text = "Crew Special Abilities")
    abilitiesList.forEach { it ->
        CrewAbilityItem(
            ability = it,
            displayDeleteAbilityDialog = { abilityDeleteDialogClick(it) },
        )
    }

    var abilityExpanded by remember { mutableStateOf(false) }
    var chosenExistingAbility by remember { mutableStateOf(CrewAbility()) }
    fun abilityChooser(ability: CrewAbility) {
        abilityExpanded = false
        chosenExistingAbility = ability
        onValueChange(crewDetails.copy(crewAbilities = crewDetails.crewAbilities + ability))
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
        Row(
            modifier = Modifier.weight(1f)
        ) {
            MyCrewAbilitySpinner(
                expanded = abilityExpanded,
                onClick = { abilityExpanded = !abilityExpanded },
                list = everyAbilityList.distinct(),
                chooser = ::abilityChooser,
                report = "Chose Ability"
            )
        }
    }
    if (showAbilityDialog) {
        NameAndDescrEntryDialog(
            title = "Add Crew Ability",
            name = newAbility.crewAbilityName,
            nameLabel = "Crew Ability Name",
            nameHint = "The name of the Ability",
            description = newAbility.crewAbilityDescription,
            descriptionLabel = "Crew Ability Description",
            descriptionHint = "Describe the new Crew Ability",
            onDismiss = { showAbilityDialog = !showAbilityDialog },
            onAccept = { doNewAbilityFromPair(it) },
            onNameChange = { onAbilityChange(it) },
            onDescriptionChange = { onDescriptionChange(it) },
        )

    }
}//end Abilities Block

//crew upgrafes block
@Composable
fun CrewUpgradesBlock(
    onValueChange: (Crew) -> Unit,
    crewDetails: Crew,
    everyUpgradeList: List<CrewUpgrade>,
) {
    var newUpgrade by remember { mutableStateOf(CrewUpgrade()) }
    var showUpgradeDialog by remember { mutableStateOf(false) }

    var everyUpgradeNameList: MutableList<CrewUpgrade> = mutableListOf()
    everyUpgradeList.forEach {
        everyUpgradeNameList.add(it)
    }
    everyUpgradeNameList = everyUpgradeNameList.distinct().toMutableList()

    fun upgradeDialogClick() {
        showUpgradeDialog = !showUpgradeDialog
    }

    fun onUpgradeChange(upgradeName: String) {
        newUpgrade = newUpgrade.copy(upgradeName = upgradeName)
    }

    fun onDescriptionChange(upgradeDescription: String) {
        newUpgrade = newUpgrade.copy(upgradeDescription = upgradeDescription)
    }

    fun doNewUpgradeFromPair(info: Pair<String, String>) {
        showUpgradeDialog = false
        onValueChange(
            crewDetails.copy(
                upgrades = crewDetails.upgrades + CrewUpgrade(
                    upgradeName = info.first,
                    upgradeDescription = info.second
                )
            )
        )
        newUpgrade = CrewUpgrade()
    }

    var showRemoveUpgradeDialog by remember { mutableStateOf(false) }
    var chosenUpgrade by remember { mutableStateOf(CrewUpgrade()) }

    fun upgradeDeleteDialogClick(upgrade: CrewUpgrade) {
        chosenUpgrade = upgrade
        showRemoveUpgradeDialog = !showRemoveUpgradeDialog
    }

    fun doRemoveUpgrade(upgrade: CrewUpgrade) {
        showRemoveUpgradeDialog = false
        val newUpgradeList = mutableListOf<CrewUpgrade>()
        crewDetails.upgrades.forEach {
            if (it != upgrade) newUpgradeList.add(it)
        }
        onValueChange(
            crewDetails.copy(
                upgrades = newUpgradeList
            )
        )
    }

    if (showRemoveUpgradeDialog) {
        DeleteConfirmationDialog(
            title = "Crew Upgrade",
            name = chosenUpgrade.upgradeName,
            onAccept = { doRemoveUpgrade(chosenUpgrade) },
            onDismiss = { showRemoveUpgradeDialog = false },
            deleteType = "Remove"
        )
    }

    val upgradesList = crewDetails.upgrades

    TitleBlock(title = "", text = "Crew Upgrades")
    upgradesList.forEach { it ->
        CrewUpgradeItem(
            upgrade = it,
            displayDeleteUpgradeDialog = { upgradeDeleteDialogClick(it) },
        )
    }

    var upgradeExpanded by remember { mutableStateOf(false) }
    var chosenExistingUpgrade by remember { mutableStateOf(CrewUpgrade()) }
    fun upgradeChooser(upgrade: CrewUpgrade) {
        upgradeExpanded = false
        chosenExistingUpgrade = upgrade
        onValueChange(crewDetails.copy(upgrades = crewDetails.upgrades + upgrade))
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            MyButton(
                onClick = { upgradeDialogClick() },
                text = "New Upgrade",
                leadingIcon = Icons.Default.Add
            )
        }
        Row(
            modifier = Modifier.weight(1f)
        ) {
            MyCrewUpgradeSpinner(
                expanded = upgradeExpanded,
                onClick = { upgradeExpanded = !upgradeExpanded },
                list = everyUpgradeList.distinct(),
                chooser = ::upgradeChooser,
                report = "Chose Upgrade"
            )
        }
    }
    if (showUpgradeDialog) {
        NameAndDescrEntryDialog(
            title = "New Contact",
            name = newUpgrade.upgradeName,
            nameLabel = "Upgrade Name",
            nameHint = "Name this Upgrade",
            description = newUpgrade.upgradeDescription,
            descriptionLabel = "Upgrade Description",
            descriptionHint = "Describe the new Upgrade",
            onDismiss = { showUpgradeDialog = !showUpgradeDialog },
            onAccept = { doNewUpgradeFromPair(it) },
            onNameChange = { onUpgradeChange(it) },
            onDescriptionChange = { onDescriptionChange(it) },
        )
    }
}//end upgrade Block

@Composable
fun CrewContactsBlock(
    onValueChange: (Crew) -> Unit,
    crewDetails: Crew,
    everyContactList: List<Contact>,
) {
    var newContact by remember { mutableStateOf(Contact()) }
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
        newContact = newContact.copy(name = contactName)
    }

    fun onDescriptionChange(description: String) {
        newContact = newContact.copy(description = description)
    }

    fun doNewContactFromPair(info: Pair<String, String>) {
        showContactDialog = false
        onValueChange(
            crewDetails.copy(
                contacts = crewDetails.contacts + Contact(
                    name = info.first,
                    description = info.second
                )
            )
        )
        newContact = Contact()
    }

    var showRemoveContactDialog by remember { mutableStateOf(false) }
    var chosenContact by remember { mutableStateOf(Contact()) }

    fun contactDeleteDialogClick(contact: Contact) {
        chosenContact = contact
        showRemoveContactDialog = !showRemoveContactDialog
    }

    fun doRemoveContact(contact: Contact) {
        showRemoveContactDialog = false
        val newContactList = mutableListOf<Contact>()
        crewDetails.contacts.forEach {
            if (it != contact) newContactList.add(it)
        }
        onValueChange(
            crewDetails.copy(
                contacts = newContactList
            )
        )
    }

    if (showRemoveContactDialog) {
        DeleteConfirmationDialog(
            title = "Contact",
            name = chosenContact.name,
            onAccept = { doRemoveContact(chosenContact) },
            onDismiss = { showRemoveContactDialog = false },
            deleteType = "Remove"
        )
    }

    val contactList = crewDetails.contacts

    TitleBlock(title = "", text = "Contacts")
    contactList.forEach { it ->
        ContactItem(
            contact = it,
            displayDeleteContactDialog = { contactDeleteDialogClick(it) },
            onClick = {}//TODO
        )
    }

    var contactExpanded by remember { mutableStateOf(false) }
    var chosenExistingContact by remember { mutableStateOf(Contact()) }
    fun contactChooser(contact: Contact) {
        contactExpanded = false
        chosenExistingContact = contact
        onValueChange(crewDetails.copy(contacts = crewDetails.contacts + contact))
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
        Row(
            modifier = Modifier.weight(1f)
        ) {
            MyContactSpinner(
                expanded = contactExpanded,
                onClick = { contactExpanded = !contactExpanded },
                list = everyContactList.distinct(),
                chooser = ::contactChooser,
                report = "Choose Contact"
            )
        }
    }
    if (showContactDialog) {
        NameAndDescrEntryDialog(
            title = "New Contact",
            name = newContact.name,
            nameLabel = "Contact Name",
            nameHint = "Name the new Contact",
            description = newContact.description,
            descriptionLabel = "Contact Description",
            descriptionHint = "Describe the Contact?",
            onDismiss = { showContactDialog = !showContactDialog },
            onAccept = { doNewContactFromPair(it) },
            onNameChange = { onContactChange(it) },
            onDescriptionChange = { onDescriptionChange(it) },
        )
    }

}//end Abilities Block

@Composable
fun CrewAbilityItem(
    ability: CrewAbility,
    modifier: Modifier = Modifier,
    enableDelete: Boolean = true,
    displayDeleteAbilityDialog: (CrewAbility) -> Unit,
    onClick: (CrewAbility) -> Unit = {}
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
                    .clickable { onClick(ability) }
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = ability.crewAbilityName,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (enableDelete) {
                    Icon(
                        imageVector = Icons.TwoTone.Delete,
                        contentDescription = "Delete",
                        Modifier
                            .clickable { displayDeleteAbilityDialog(ability) }
                            .align(Alignment.CenterVertically)
                            .size(25.dp)
                    )
                }
            }
        }
    }
}//end AbilityItem

@Composable
fun CrewUpgradeItem(
    upgrade: CrewUpgrade,
    modifier: Modifier = Modifier,
    enableDelete: Boolean = true,
    displayDeleteUpgradeDialog: (CrewUpgrade) -> Unit,
    onClick: (CrewUpgrade) -> Unit = {}
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
                    .clickable { onClick(upgrade) }
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = upgrade.upgradeName,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (enableDelete) {
                    Icon(
                        imageVector = Icons.TwoTone.Delete,
                        contentDescription = "Delete",
                        Modifier
                            .clickable { displayDeleteUpgradeDialog(upgrade) }
                            .align(Alignment.CenterVertically)
                            .size(25.dp)
                    )
                }
            }
        }
    }
}//end AbilityItem
