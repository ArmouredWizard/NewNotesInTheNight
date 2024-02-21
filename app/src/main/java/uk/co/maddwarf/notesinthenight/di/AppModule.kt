package uk.co.maddwarf.databaseinthedark.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.co.maddwarf.notesinthenight.database.NightDao
import uk.co.maddwarf.notesinthenight.database.NightDatabase
import uk.co.maddwarf.notesinthenight.repository.NightRepository
import uk.co.maddwarf.notesinthenight.repository.NightRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationComponent (i.e. everywhere in the application)
    @Provides
    fun provideNightDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        NightDatabase::class.java,
        "night_db"
    )
        .fallbackToDestructiveMigration()
        .build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideNightDao(db: NightDatabase) = db.getNightDao()

    @Singleton
    @Provides
    fun provideNightRepository(nightDao: NightDao): NightRepository =
        NightRepositoryImpl(nightDao)


}