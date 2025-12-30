package com.studyando.di

import android.content.Context
import androidx.room.Room
import com.studyando.data.local.StudyandoDatabase
import com.studyando.data.repo.StudyRepository
import com.studyando.data.prefs.SettingsDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides @Singleton
    fun provideDb(@ApplicationContext ctx: Context): StudyandoDatabase =
        Room.databaseBuilder(ctx, StudyandoDatabase::class.java, "studyando.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides @Singleton
    fun provideRepo(db: StudyandoDatabase) = StudyRepository(db)

    @Provides @Singleton
    fun provideSettings(@ApplicationContext ctx: Context) = SettingsDataStore(ctx)
}