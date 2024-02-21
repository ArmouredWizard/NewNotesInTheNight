package uk.co.maddwarf.notesinthenight.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uk.co.maddwarf.notesinthenight.database.converters.Converters
import uk.co.maddwarf.notesinthenight.database.entities.ContactEntity
import uk.co.maddwarf.notesinthenight.database.entities.CrewAbilityCrossRef
import uk.co.maddwarf.notesinthenight.database.entities.CrewAbilityEntity
import uk.co.maddwarf.notesinthenight.database.entities.CrewContactCrossRef
import uk.co.maddwarf.notesinthenight.database.entities.CrewEntity
import uk.co.maddwarf.notesinthenight.database.entities.CrewUpgradeCrossRef
import uk.co.maddwarf.notesinthenight.database.entities.CrewUpgradeEntity
import uk.co.maddwarf.notesinthenight.database.entities.NoteEntity
import uk.co.maddwarf.notesinthenight.database.entities.ScoundrelAbilityCrossRef
import uk.co.maddwarf.notesinthenight.database.entities.ScoundrelContactCrossRef
import uk.co.maddwarf.notesinthenight.database.entities.ScoundrelEntity
import uk.co.maddwarf.notesinthenight.database.entities.SpecialAbilityEntity

@Database(
    entities = [
        ScoundrelEntity::class,
        CrewEntity::class,
        ContactEntity::class,
        SpecialAbilityEntity::class,
        CrewAbilityEntity::class,
        CrewUpgradeEntity::class,
        CrewAbilityCrossRef::class,
        CrewContactCrossRef::class,
        CrewUpgradeCrossRef::class,
        ScoundrelAbilityCrossRef::class,
        ScoundrelContactCrossRef::class,
        NoteEntity::class
    ],
    version = 14,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class NightDatabase : RoomDatabase() {

    abstract fun getNightDao(): NightDao
}