package uk.co.maddwarf.notesinthenight.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uk.co.maddwarf.notesinthenight.database.NightDao
import uk.co.maddwarf.notesinthenight.database.entities.CrewAbilityCrossRef
import uk.co.maddwarf.notesinthenight.database.entities.CrewAbilityEntity
import uk.co.maddwarf.notesinthenight.database.entities.CrewContactCrossRef
import uk.co.maddwarf.notesinthenight.database.entities.CrewUpgradeCrossRef
import uk.co.maddwarf.notesinthenight.database.entities.NoteEntity
import uk.co.maddwarf.notesinthenight.database.entities.ScoundrelAbilityCrossRef
import uk.co.maddwarf.notesinthenight.database.entities.ScoundrelContactCrossRef
import uk.co.maddwarf.notesinthenight.database.entities.toContact
import uk.co.maddwarf.notesinthenight.database.entities.toContactEntity
import uk.co.maddwarf.notesinthenight.database.entities.toCrew
import uk.co.maddwarf.notesinthenight.database.entities.toCrewAbility
import uk.co.maddwarf.notesinthenight.database.entities.toCrewAbilityEntity
import uk.co.maddwarf.notesinthenight.database.entities.toCrewEntity
import uk.co.maddwarf.notesinthenight.database.entities.toCrewUpgrade
import uk.co.maddwarf.notesinthenight.database.entities.toCrewUpgradeEntity
import uk.co.maddwarf.notesinthenight.database.entities.toNote
import uk.co.maddwarf.notesinthenight.database.entities.toNoteEntity
import uk.co.maddwarf.notesinthenight.database.entities.toScoundrel
import uk.co.maddwarf.notesinthenight.database.entities.toScoundrelEntity
import uk.co.maddwarf.notesinthenight.database.entities.toSpecialAbility
import uk.co.maddwarf.notesinthenight.database.entities.toSpecialAbilityEntity
import uk.co.maddwarf.notesinthenight.model.Contact
import uk.co.maddwarf.notesinthenight.model.Crew
import uk.co.maddwarf.notesinthenight.model.CrewAbility
import uk.co.maddwarf.notesinthenight.model.CrewUpgrade
import uk.co.maddwarf.notesinthenight.model.Note
import uk.co.maddwarf.notesinthenight.model.Scoundrel
import uk.co.maddwarf.notesinthenight.model.SpecialAbility
import javax.inject.Inject

interface NightRepository {
    fun getListOfScoundrels(): Flow<List<Scoundrel>>
    suspend fun deleteScoundrel(scoundrel: Scoundrel)
    fun getListOfFullScoundrels(): Flow<List<Scoundrel>>
    suspend fun saveScoundrel(scoundrel: Scoundrel)
    fun getListOfAbilities(): Flow<List<SpecialAbility>>
    fun getListOfContacts(): Flow<List<Contact>>
    fun getListOfCrews(): Flow<List<Crew>>
    fun getFullScoundrelDetails(scoundrelId: Int): Flow<Scoundrel>
    suspend fun saveEditedScoundrel(scoundrel: Scoundrel)
    suspend fun deleteCrew(crew: Crew)
    fun getFullCrewData(crewId: Int): Flow<Crew>
    suspend fun saveFullCrew(crew: Crew)
    fun getListOfCrewAbilities(): Flow<List<CrewAbility>>
    fun getListOfUpgrades(): Flow<List<CrewUpgrade>>
    fun getScoundrelsByCrewId(crewId: Int): Flow<List<Scoundrel>>
    suspend fun saveContact(contact: Contact)
    suspend fun deleteContact(contact: Contact)
    suspend fun saveEditedCrew(crew: Crew)
    suspend fun deleteCrewUpgrade(crewUpgrade: CrewUpgrade)
    suspend fun saveCrewUpgrade(crewUpgrade: CrewUpgrade)
    suspend fun saveCrewAbility(crewAbility: CrewAbility): Long
    suspend fun deleteCrewAbility(crewAbility: CrewAbility): Any
    suspend fun saveAbility(ability: SpecialAbility)
    suspend fun deleteAbility(ability: SpecialAbility)

    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note:Note)
    fun getAllNotes(): Flow<List<Note>>
    fun getAllNotesByCategory(category:String): Flow<List<Note>>
    fun getNoteById(noteId:Int): Flow<Note>

}

class NightRepositoryImpl @Inject constructor(private val nightDao: NightDao) : NightRepository {
    override fun getListOfScoundrels(): Flow<List<Scoundrel>> =
        nightDao.getListOfScoundrels().map {
            it.map { scoundrel ->
                scoundrel.toScoundrel()
            }
        }

    override suspend fun deleteScoundrel(scoundrel: Scoundrel) =
        nightDao.deleteScoundrel(scoundrel.toScoundrelEntity())

    override fun getListOfFullScoundrels(): Flow<List<Scoundrel>> =
        nightDao.getListOfFullScoundrels().map {
            it.map { scoundrel ->
                scoundrel.toScoundrel()
            }
        }

    override suspend fun saveScoundrel(scoundrel: Scoundrel) {
        Log.d("SCOUNDREL", scoundrel.toString())

        var crewId: Int? = null
        if (scoundrel.crew != null) {
            crewId = nightDao.saveCrew(scoundrel.crew!!.toCrewEntity()).toInt()
            if (crewId == -1) crewId = scoundrel.crew!!.crewId
        }

        val scoundrelId =
            nightDao.saveScoundrel(scoundrel.toScoundrelEntity().copy(crewId = crewId)).toInt()

        scoundrel.specialAbilities.forEach { ability ->
            var abilityId = nightDao.insertAbility(ability.toSpecialAbilityEntity())
            if (abilityId == -1L) {
                abilityId = ability.abilityId.toLong()
            }
            nightDao.insertScoundrelAbilityCrossRef(
                ScoundrelAbilityCrossRef(
                    scoundrelId = scoundrelId.toInt(),
                    abilityId = abilityId.toInt()
                )
            )
        }

        scoundrel.contacts.forEach { contact ->
            var contactId = nightDao.insertContact(contact.toContactEntity())
            if (contactId == -1L) {
                contactId = contact.id.toLong()
            }
            nightDao.insertScoundrelContactCrossRef(
                ScoundrelContactCrossRef(
                    scoundrelId = scoundrelId,
                    contactId = contactId.toInt()
                )
            )
        }

    }//end save scoundrel

    override fun getListOfAbilities(): Flow<List<SpecialAbility>> =
        nightDao.getListOfAbilities().map {
            it.map { ability ->
                ability.toSpecialAbility()
            }
        }

    override fun getListOfContacts(): Flow<List<Contact>> =
        nightDao.getListOfContacts().map {
            it.map { contact ->
                contact.toContact()
            }
        }

    override fun getListOfCrews(): Flow<List<Crew>> =
        nightDao.getListOfCrews().map {
            it.map { crew ->
                crew.toCrew()
            }
        }

    override fun getFullScoundrelDetails(scoundrelId: Int): Flow<Scoundrel> {
        return nightDao.selectFullScoundrelDataById(scoundrelId)
            .map { scoundrelEntity ->
                scoundrelEntity.toScoundrel()
            }
    }

    override suspend fun saveEditedScoundrel(scoundrel: Scoundrel) {
        Log.d("EDIT CREW TO SAVE IN REPO", scoundrel.toString())
        var crewId: Int? = null
        if (scoundrel.crew != null) {
            crewId = nightDao.saveCrew(scoundrel.crew!!.toCrewEntity()).toInt()
            if (crewId == -1) crewId = scoundrel.crew!!.crewId
        }

        nightDao.updateScoundrel(scoundrel.toScoundrelEntity().copy(crewId = crewId))

        val specialAbilityList = scoundrel.specialAbilities
        //remove Special Ability by Scoundrel ID
        nightDao.deleteSpecialAbilityCrossRefById(scoundrel.scoundrelId)
//insert updated specialAbility-scoundrel links
        specialAbilityList.forEach {
            var specialAbilityId = nightDao.insertAbility(it.toSpecialAbilityEntity())
            if (specialAbilityId == -1L) {
                specialAbilityId = it.abilityId.toLong()
            }
            nightDao.insertScoundrelAbilityCrossRef(
                ScoundrelAbilityCrossRef(
                    scoundrelId = scoundrel.scoundrelId,
                    abilityId = specialAbilityId.toInt()
                )
            )
        }//end forEach SpecialAbility

        //remove Contacts by scoundrel ID
        nightDao.deleteContactsCrossRefById(scoundrel.scoundrelId)
//insert updated Contact list
        scoundrel.contacts.forEach { contact ->
            var contactId = nightDao.saveContact(contact.toContactEntity())
            if (contactId == -1L) {
                contactId = contact.id.toLong()
            }
            nightDao.insertScoundrelContactCrossRef(
                ScoundrelContactCrossRef(
                    scoundrelId = scoundrel.scoundrelId,
                    contactId = contactId.toInt()
                )
            )
        }


    }//end edit scoundrel

    override suspend fun deleteCrew(crew: Crew) {
        nightDao.deleteCrew(crew.toCrewEntity())
    }

    override fun getFullCrewData(crewId: Int): Flow<Crew> =
        nightDao.selectFullCrewData(crewId).map {
            it.toCrew()
        }

    override suspend fun saveFullCrew(crew: Crew) {
        val crewId = nightDao.saveCrew(crew.toCrewEntity())

//todo keep up with additions to crew data
        crew.crewAbilities.forEach { ability ->
            var abilityId = nightDao.insertCrewAbility(ability.toCrewAbilityEntity())
            if (abilityId == -1L) {
                abilityId = ability.crewAbilityId.toLong()
            }
            nightDao.insertCrewAbilityCrossRef(
                CrewAbilityCrossRef(
                    crewId = crewId.toInt(),
                    crewAbilityId = abilityId.toInt()
                )
            )
        }

        nightDao.deleteCrewContactsCrossRefByCrewId(crew.crewId)
//insert updated contact list
        crew.contacts.forEach { contact ->
            var contactId = nightDao.saveContact(contact.toContactEntity())
            if (contactId == -1L) {
                contactId = contact.id.toLong()
            }
            nightDao.insertCrewContactCrossRef(
                CrewContactCrossRef(
                    crewId = crewId.toInt(),
                    contactId = contactId.toInt()
                )
            )
        }

        nightDao.deleteCrewUpgradeCrossRefByCrewId(crew.crewId)
        crew.upgrades.forEach { upgrade ->
            var upgradeId = nightDao.insertCrewUpgrade(upgrade.toCrewUpgradeEntity())
            if (upgradeId == -1L) {
                upgradeId = upgrade.upgradeId.toLong()
            }
            nightDao.insertCrewUpgradeCrossRef(
                CrewUpgradeCrossRef(
                    crewId = crewId.toInt(),
                    upgradeId = upgradeId.toInt()
                )
            )
        }
    }//save full crew

    override suspend fun saveEditedCrew(crew: Crew) {
        nightDao.updateCrew(crew.toCrewEntity())
        Log.d("CREW", crew.toString())

//todo keep up with additions to crew data
        nightDao.deleteCrewAbilityCrossRefByCrewId(crew.crewId)
        crew.crewAbilities.forEach { ability ->
            var abilityId = nightDao.insertCrewAbility(ability.toCrewAbilityEntity())
            if (abilityId == -1L) {
                abilityId = ability.crewAbilityId.toLong()
            }
            nightDao.insertCrewAbilityCrossRef(
                CrewAbilityCrossRef(
                    crewId = crew.crewId.toInt(),
                    crewAbilityId = abilityId.toInt()
                )
            )
        }

        nightDao.deleteCrewContactsCrossRefByCrewId(crew.crewId)
//insert updated contact list
        crew.contacts.forEach { contact ->
            var contactId = nightDao.saveContact(contact.toContactEntity())
            if (contactId == -1L) {
                contactId = contact.id.toLong()
            }
            nightDao.insertCrewContactCrossRef(
                CrewContactCrossRef(
                    crewId = crew.crewId.toInt(),
                    contactId = contactId.toInt()
                )
            )
        }

        nightDao.deleteCrewUpgradeCrossRefByCrewId(crew.crewId)
        crew.upgrades.forEach { upgrade ->
            var upgradeId = nightDao.insertCrewUpgrade(upgrade.toCrewUpgradeEntity())
            if (upgradeId == -1L) {
                upgradeId = upgrade.upgradeId.toLong()
            }
            nightDao.insertCrewUpgradeCrossRef(
                CrewUpgradeCrossRef(
                    crewId = crew.crewId.toInt(),
                    upgradeId = upgradeId.toInt()
                )
            )
        }
    }//end save edited crew

    override fun getListOfCrewAbilities(): Flow<List<CrewAbility>> =
        nightDao.getListOfCrewAbilities().map {
            it.map { ability: CrewAbilityEntity ->
                ability.toCrewAbility()
            }
        }

    override fun getListOfUpgrades(): Flow<List<CrewUpgrade>> =
        nightDao.getAllUpgrades().map {
            it.map { upgrade ->
                upgrade.toCrewUpgrade()
            }
        }

    override fun getScoundrelsByCrewId(crewId: Int): Flow<List<Scoundrel>> =
        nightDao.getScoundrelsByCrewId(crewId).map {
            it.map { scoundrel ->
                scoundrel.toScoundrel()
            }
        }

    override suspend fun saveContact(contact: Contact) {
        nightDao.saveContact(contact.toContactEntity())
    }

    override suspend fun deleteContact(contact: Contact) {
        nightDao.deleteContact(contact.toContactEntity())
    }

    override suspend fun deleteCrewUpgrade(crewUpgrade: CrewUpgrade) {
        nightDao.deleteCrewUpgrade(crewUpgrade.toCrewUpgradeEntity())
    }

    override suspend fun saveCrewUpgrade(crewUpgrade: CrewUpgrade) {
        nightDao.insertCrewUpgrade(crewUpgrade.toCrewUpgradeEntity())
    }

    override suspend fun saveCrewAbility(crewAbility: CrewAbility) =
        nightDao.insertCrewAbility(crewAbility.toCrewAbilityEntity())

    override suspend fun deleteCrewAbility(crewAbility: CrewAbility) =
        nightDao.deleteCrewAbility(crewAbility.toCrewAbilityEntity())

    override suspend fun saveAbility(ability: SpecialAbility) {
        nightDao.insertAbility(ability.toSpecialAbilityEntity())
    }

    override suspend fun deleteAbility(ability: SpecialAbility) {
        nightDao.deleteSpecialAbility(ability.toSpecialAbilityEntity())
        nightDao.deleteSpecialAbilityCrossRefByAbilityId(ability.abilityId)
    }

    override suspend fun insertNote(note: Note) =
        nightDao.insertNote(note.toNoteEntity())

    override suspend fun deleteNote(note: Note) =
        nightDao.deleteNote(note.toNoteEntity())

    override fun getAllNotes():Flow<List<Note>> =
        nightDao.getAllNotes().map{
            it.map{note->
                note.toNote()
            }
        }

    override fun getAllNotesByCategory(category: String):Flow<List<Note>> =
        nightDao.getAllNotesByCategory(category).map {
            it.map{note->
                note.toNote()
            }
        }

    override fun getNoteById(noteId: Int) :Flow<Note> =
        nightDao.getNoteById(noteId).map{
            it.toNote()
        }
}