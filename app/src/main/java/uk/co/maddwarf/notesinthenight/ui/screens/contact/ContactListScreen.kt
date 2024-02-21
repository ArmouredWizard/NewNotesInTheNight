package uk.co.maddwarf.notesinthenight.ui.screens.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import uk.co.maddwarf.notesinthenight.model.Contact
import uk.co.maddwarf.notesinthenight.navigation.NavigationDestination
import uk.co.maddwarf.notesinthenight.ui.composables.DeleteConfirmationDialog
import uk.co.maddwarf.notesinthenight.ui.composables.InfoPopUp
import uk.co.maddwarf.notesinthenight.ui.composables.NameAndDescrEntryDialog
import uk.co.maddwarf.notesinthenight.ui.composables.contact.ContactItem

object ContactListDestination : NavigationDestination {
    override val route = "contact_list"
    override val titleRes = R.string.contact_list
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(
    navigateToHome: () -> Unit,
    onNavigateUp: () -> Unit,
    navigateBack: () -> Unit,
    contactListViewModel: ContactListViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()

    val uiState by contactListViewModel.contactListUiState.collectAsState()

    var newContact by remember { mutableStateOf(Contact()) }
    var showAddContactDialog by remember { mutableStateOf(false) }
    fun addContactClick() {
        showAddContactDialog = !showAddContactDialog
    }

    fun onContactChange(conactName: String) {
        newContact = newContact.copy(name = conactName)
    }

    fun onDescriptionChange(contactDescription: String) {
        newContact = newContact.copy(description = contactDescription)
    }

    fun doNewContactFromPair(info: Pair<String, String>) {
        showAddContactDialog = false
        coroutineScope.launch {
            contactListViewModel.saveContact(Contact(
                name = info.first,
                description = info.second
            ))
        }
        newContact = Contact()
    }

    if (showAddContactDialog) {
        NameAndDescrEntryDialog(
            title = "New Contact",
            name = newContact.name,
            nameLabel = "Contact Name",
            nameHint = "Name this Contact",
            description = newContact.description,
            descriptionLabel = "Contact Description",
            descriptionHint = "Describe the new Contact",
            onDismiss = { showAddContactDialog = !showAddContactDialog },
            onAccept = { doNewContactFromPair(it) },
            onNameChange = { onContactChange(it) },
            onDescriptionChange = { onDescriptionChange(it) },
        )

    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NotesInTheNightTopAppBar(
                title = "List of Contacts",
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior,
                navigateToHome = navigateToHome
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { addContactClick() },
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
        ContactListBody(
            contactListViewModel = contactListViewModel,
            contactList = uiState.list,
            onItemClick = {},
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }//end Scaffold

}//end Contact List Screen

@Composable
fun ContactListBody(
    contactListViewModel: ContactListViewModel,
    contactList: List<Contact>,
    onItemClick: (Contact) -> Unit,
    modifier: Modifier
) {

    val coroutineScope = rememberCoroutineScope()

    var displayDeleteContactDialog by remember { mutableStateOf(false) }
    var chosenContact by remember { mutableStateOf(Contact()) }

    fun doRemoveContact(contact: Contact) {
        displayDeleteContactDialog = false
        coroutineScope.launch {
            contactListViewModel.deleteContact(contact)
        }
    }

    fun showDeleteContact(contact: Contact) {
        chosenContact = contact
        displayDeleteContactDialog = !displayDeleteContactDialog
    }

    if (displayDeleteContactDialog) {
        DeleteConfirmationDialog(
            title = "Contact",
            name = chosenContact.name,
            onAccept = { doRemoveContact(chosenContact) },
            onDismiss = { displayDeleteContactDialog = false }
        )
    }

    var showContactPopUp by remember { mutableStateOf(false) }
    var chosenContactToShow by remember { mutableStateOf(Contact()) }
    fun doContactPopUp(contact: Contact) {
        chosenContactToShow = contact
        showContactPopUp = !showContactPopUp
    }

    if (showContactPopUp) {
        InfoPopUp(
            title = "Contact",
            firstTextTitle = "Contact Name",
            firstText = chosenContactToShow.name,
            secondTextTitle = "Contact Description",
            secondText = chosenContactToShow.description,
            onDismiss = { showContactPopUp = !showContactPopUp }
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
            if (contactList.isEmpty()) {
                Text(text = "NO CONTACTS")
            } else {
//FILTERS GO HERE
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(items = contactList, key = { it.id }) { item ->
                        ContactItem(
                            contact = item,
                            modifier = Modifier
                                .padding(6.dp),
                            displayDeleteContactDialog = { showDeleteContact(item) },
                            onClick = { doContactPopUp(item) }
                        )
                    }
                }
            }//end IF List
        }//end column
    }//end Box
}//end Scoundrel List Body