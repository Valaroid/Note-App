package com.example.mvp2projects.data.di


import android.content.Context
import androidx.room.Room
import com.example.mvp2projects.data.db.NoteDataBase
import com.example.mvp2projects.data.model.NoteEntity
import com.example.mvp2projects.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleDb {

    @Provides
    fun provideNoteEntity() = NoteEntity()

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context) = Room
        .databaseBuilder(context, NoteDataBase::class.java, Constants.NOTE_DATABASE)
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()


    @Provides
    @Singleton
    fun provideDao(db: NoteDataBase)=db.getDao()


}