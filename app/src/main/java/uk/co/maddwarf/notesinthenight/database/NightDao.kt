package uk.co.maddwarf.notesinthenight.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import uk.co.maddwarf.notesinthenight.database.entities.ContactEntity
import uk.co.maddwarf.notesinthenight.database.entities.CrewAbilityCrossRef
import uk.co.maddwarf.notesinthenight.database.entities.CrewAbilityEntity
import uk.co.maddwarf.notesinthenight.database.entities.CrewContactCrossRef
import uk.co.maddwarf.notesinthenight.database.entities.CrewEntity
import uk.co.maddwarf.notesinthenight.database.entities.CrewUpgradeCrossRef
import uk.co.maddwarf.notesinthenight.database.entities.CrewUpgradeEntity
import uk.co.maddwarf.notesinthenight.database.entities.FullCrewEntity
import uk.co.maddwarf.notesinthenight.database.entities.FullNoteEntity
import uk.co.maddwarf.notesinthenight.database.entities.FullScoundrelEntity
import uk.co.maddwarf.notesinthenight.database.entities.NoteEntity
import uk.co.maddwarf.notesinthenight.database.entities.NoteTagCrossRef
import uk.co.maddwarf.notesinthenight.database.entities.ScoundrelAbilityCrossRef
import uk.co.maddwarf.notesinthenight.database.entities.ScoundrelContactCrossRef
import uk.co.maddwarf.notesinthenight.database.entities.ScoundrelEntity
import uk.co.maddwarf.notesinthenight.database.entities.SpecialAbilityEntity
import uk.co.maddwarf.notesinthenight.database.entities.TagEntity

@Dao
interface NightDao {

    @Query("SELECT * from scoundrels")
    fun getListOfScoundrels(): Flow<List<ScoundrelEntity>>

    @Delete
    suspend fun deleteScoundrel(scoundrelEntity: ScoundrelEntity)

    @Query("SELECT * from scoundrels")
    fun getListOfFullScoundrels(): Flow<List<FullScoundrelEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveScoundrel(scoundrelEntity: ScoundrelEntity): Long

    @Query("SELECT * from specialabilities")
    fun getListOfAbilities(): Flow<List<SpecialAbilityEntity>>

    @Query("SELECT * from contacts")
    fun getListOfContacts(): Flow<List<ContactEntity>>

    @Query("SELECT * from crews")
    fun getListOfCrews(): Flow<List<CrewEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCrew(crewEntity: CrewEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)//todo check on conflict
    suspend fun insertAbility(abilityEntity: SpecialAbilityEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)//todo check on conflict
    suspend fun insertScoundrelAbilityCrossRef(scoundrelAbilityCrossRef: ScoundrelAbilityCrossRef)

    @Query("DELETE from ScoundrelAbilityCrossRef where scoundrelId = :id")
    suspend fun deleteSpecialAbilityCrossRefById(id: Int)

    @Delete
    suspend fun deleteSpecialAbility(ability: SpecialAbilityEntity)

    @Transaction
    @Query("DELETE from ScoundrelAbilityCrossRef where abilityId = :id")
    suspend fun deleteSpecialAbilityCrossRefByAbilityId(id: Int)

    @Transaction
    @Query("SELECT * from scoundrels where scoundrelId = :scoundrelId")
    fun selectFullScoundrelDataById(scoundrelId: Int): Flow<FullScoundrelEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertContact(contactEntity: ContactEntity): Long

    @Delete
    suspend fun deleteContact(contactEntity: ContactEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertScoundrelContactCrossRef(scoundrelContactCrossRef: ScoundrelContactCrossRef)

    @Update
    suspend fun updateScoundrel(toScoundrelEntity: ScoundrelEntity)

    @Transaction
    @Query("DELETE from ScoundrelContactCrossRef where scoundrelId = :id")
    suspend fun deleteContactsCrossRefById(id: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveContact(contactEntity: ContactEntity): Long

    @Delete
    suspend fun deleteCrew(crew: CrewEntity)

    @Update
    suspend fun updateCrew(crewEntity: CrewEntity)

    @Transaction
    @Query("SELECT * from crews where crewId = :crewId")
    fun selectFullCrewData(crewId: Int): Flow<FullCrewEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCrewAbility(abilityEntity: CrewAbilityEntity): Long

    @Insert
    suspend fun insertCrewAbilityCrossRef(crewAbilityCrossRef: CrewAbilityCrossRef)

    @Transaction
    @Query("DELETE from CrewAbilityCrossRef where crewId = :id")
    suspend fun deleteCrewAbilityCrossRefByCrewId(id: Int)

    @Transaction
    @Query("DELETE from CrewContactCrossRef where crewId = :id")
    suspend fun deleteCrewContactsCrossRefByCrewId(id: Int)

    @Insert
    suspend fun insertCrewContactCrossRef(crewContactCrossRef: CrewContactCrossRef)

    @Query("DELETE from CrewUpgradeCrossRef where crewId = :id")
    suspend fun deleteCrewUpgradeCrossRefByCrewId(id: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCrewUpgrade(crewUpgradeEntity: CrewUpgradeEntity): Long

    @Insert
    suspend fun insertCrewUpgradeCrossRef(crossRef: CrewUpgradeCrossRef)

    @Query("SELECT * from crewabilities")
    fun getListOfCrewAbilities(): Flow<List<CrewAbilityEntity>>

    @Query("SELECT * from crewupgrades")
    fun getAllUpgrades(): Flow<List<CrewUpgradeEntity>>

    @Query("SELECT * from scoundrels where crewId = :crewId")
    fun getScoundrelsByCrewId(crewId: Int): Flow<List<ScoundrelEntity>>

    @Delete
    suspend fun deleteCrewAbility(crewAbilityEntity: CrewAbilityEntity)

    @Delete
    suspend fun deleteCrewUpgrade(crewUpgrade: CrewUpgradeEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: NoteEntity): Long

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("SELECT * from notes")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * from notes")
    fun getAllFullNotes(): Flow<List<FullNoteEntity>>

    @Query("SELECT * from notes where noteId = :noteId")
    fun getNoteById(noteId: Int): Flow<NoteEntity>

    @Query("SELECT * from tags")
    fun getNotesTags(): Flow<List<TagEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTag(tagEntity: TagEntity): Long

    @Query("SELECT tagId from tags where tag = :tag")
    fun getTagIdByTag(tag: String): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNoteTagCrossRef(noteTagCrossRef: NoteTagCrossRef)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Transaction
    @Query("DELETE from NoteTagCrossRef where noteId = :noteId")
    suspend fun deleteNoteTagCrossRefByNoteId(noteId: Int) {

    }


}