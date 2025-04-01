package com.example.mvp2projects.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvp2projects.data.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
 abstract class NoteDataBase: RoomDatabase()  {
    abstract fun getDao(): NoteDao
}