package org.lotka.xenonx.di

import android.app.Application
import androidx.room.Room

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.lotka.xenonx.data.local.NoteDatabase
import org.lotka.xenonx.data.repository.NoteRepositoryImpl
import org.lotka.xenonx.domain.repository.NoteRepository
import org.lotka.xenonx.domain.usecase.AllNotesUseCases
import org.lotka.xenonx.domain.usecase.DeleteNoteUseCase
import org.lotka.xenonx.domain.usecase.GetNoteByIdUseCase
import org.lotka.xenonx.domain.usecase.GetNotesUseCase
import org.lotka.xenonx.domain.usecase.InsertNoteUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): AllNotesUseCases {
        return AllNotesUseCases(
            getNotes = GetNotesUseCase(repository),
            deleteNote = DeleteNoteUseCase(repository),
            addNote = InsertNoteUseCase(repository),
            getNoteById = GetNoteByIdUseCase(repository)
        )
    }
}