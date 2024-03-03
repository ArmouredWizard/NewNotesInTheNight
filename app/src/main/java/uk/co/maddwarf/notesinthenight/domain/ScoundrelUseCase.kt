package uk.co.maddwarf.notesinthenight.domain

import kotlinx.coroutines.flow.Flow
import uk.co.maddwarf.notesinthenight.model.Contact
import uk.co.maddwarf.notesinthenight.model.ContactWithRating
import uk.co.maddwarf.notesinthenight.model.Crew
import uk.co.maddwarf.notesinthenight.model.CrewAbility
import uk.co.maddwarf.notesinthenight.model.CrewUpgrade
import uk.co.maddwarf.notesinthenight.model.Note
import uk.co.maddwarf.notesinthenight.model.Scoundrel
import uk.co.maddwarf.notesinthenight.model.SpecialAbility
import uk.co.maddwarf.notesinthenight.model.Tag
import uk.co.maddwarf.notesinthenight.repository.NightRepository
import javax.inject.Inject

class ScoundrelUseCase @Inject constructor(private val nightRepository: NightRepository) {

    fun getFullScoundrelData(scoundrelId: Int): Flow<Scoundrel> =
        nightRepository.getFullScoundrelDetails(scoundrelId)

    fun getListOfScoundrels(): Flow<List<Scoundrel>> =
        nightRepository.getListOfScoundrels()

    suspend fun deleteScoundrel(scoundrel: Scoundrel) =
        nightRepository.deleteScoundrel(scoundrel)

    fun getListOfFullScoundrels(): Flow<List<Scoundrel>> =
        nightRepository.getListOfFullScoundrels()

    suspend fun saveScoundrel(scoundrel: Scoundrel) {
        nightRepository.saveScoundrel(scoundrel)
    }

    fun getListOfAbilities(): Flow<List<SpecialAbility>> =
        nightRepository.getListOfAbilities()

    fun getListOfContacts(): Flow<List<Contact>> =
        nightRepository.getListOfContacts()

    fun getListOfCrews(): Flow<List<Crew>> =
        nightRepository.getListOfCrews()

    suspend fun saveEditedScoundrel(scoundrel: Scoundrel) {
        nightRepository.saveEditedScoundrel(scoundrel)
    }

    suspend fun deleteCrew(crew: Crew) =
        nightRepository.deleteCrew(crew)

    fun getFullCrewData(crewId: Int): Flow<Crew> =
        nightRepository.getFullCrewData(crewId)

    suspend fun saveFullCrew(crew: Crew) =
        nightRepository.saveFullCrew(crew)

    fun getListOfCrewAbilities() =
        nightRepository.getListOfCrewAbilities()

    fun getListOfUpgrades(): Flow<List<CrewUpgrade>> =
        nightRepository.getListOfUpgrades()

    fun getScoundrelsByCrewId(crewId: Int): Flow<List<Scoundrel>> =
        nightRepository.getScoundrelsByCrewId(crewId)

    suspend fun saveEditedCrew(crew: Crew) =
        nightRepository.saveEditedCrew(crew)

    suspend fun saveContact(contact: Contact) =
        nightRepository.saveContact(contact)

    suspend fun deleteContact(contact: Contact) =
        nightRepository.deleteContact(contact)

    suspend fun saveCrewUpgrade(crewUpgrade: CrewUpgrade) {
        nightRepository.saveCrewUpgrade(crewUpgrade)
    }

    suspend fun deleteCrewUpgrade(crewUpgrade: CrewUpgrade) {
        nightRepository.deleteCrewUpgrade(crewUpgrade)
    }

    suspend fun saveCrewAbility(crewAbility: CrewAbility) =
        nightRepository.saveCrewAbility(crewAbility)

    suspend fun deleteCrewAbility(crewAbility: CrewAbility) =
        nightRepository.deleteCrewAbility(crewAbility)

    suspend fun saveAbility(ability: SpecialAbility) =
        nightRepository.saveAbility(ability)

    suspend fun deleteAbility(ability: SpecialAbility) =
        nightRepository.deleteAbility(ability)


    suspend fun saveNote(note: Note) =
        nightRepository.saveFullNote(note)

    suspend fun deleteNote(note: Note) =
        nightRepository.deleteNote(note)

    fun getAllNotes(): Flow<List<Note>> =
        nightRepository.getAllNotes()

    fun getAllFullNotes(): Flow<List<Note>> =
        nightRepository.getAllFullNotes()

/*
    fun getAllNotesByCategory(category: String): Flow<List<Note>> =
        nightRepository.getAllNotesByCategory(category)
*/

    fun getNoteById(noteId: Int): Flow<Note> =
        nightRepository.getNoteById(noteId)

    fun getNotesTags():Flow<List<Tag>> =
        nightRepository.getNotesTags()

    suspend fun saveEditedNote(note: Note) {
        nightRepository.saveEditedNote(note)
    }

    fun getContactWithRatingByScoundrelId(scoundrelId: Int):Flow<List<ContactWithRating>> =
        nightRepository.getContactWithRatingByScoundrelId(scoundrelId)

    fun getContactWithRatingByCrewId(crewId: Int):Flow<List<ContactWithRating>> =
        nightRepository.getContactWithRatingByCrewId(crewId)


}