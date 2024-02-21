package uk.co.maddwarf.notesinthenight.ui.screens.contact

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import uk.co.maddwarf.notesinthenight.domain.ScoundrelUseCase
import uk.co.maddwarf.notesinthenight.model.Contact
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val scoundrelUseCase: ScoundrelUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val contactListUiState: StateFlow<ContactListUiState> =
        scoundrelUseCase.getListOfContacts()
            .map { ContactListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = ContactListUiState()
            )

    var contactList = scoundrelUseCase.getListOfContacts()

    suspend fun saveContact(contact: Contact) = scoundrelUseCase.saveContact(contact)

    suspend fun deleteContact(contact: Contact) = scoundrelUseCase.deleteContact(contact)

}//end ViewModel

data class ContactListUiState(
    val list: List<Contact> = listOf()
)