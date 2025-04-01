package com.example.mvp2projects.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mvp2projects.data.model.NoteEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table")
    fun getAllNotes():Observable<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note: NoteEntity): Completable

    @Update
    fun updateNote(note: NoteEntity) : Completable

    @Delete
    fun deleteNote(note: NoteEntity) : Completable

    @Query("SELECT * FROM note_table WHERE id=:id")
    fun getNoteById(id : Int) : Single<NoteEntity>


    @Query("SELECT * FROM note_table WHERE title LIKE '%' || :titleNote || '%' ")
    fun searchNoteByName(titleNote: String): Observable<List<NoteEntity>>

    @Query("SELECT * FROm note_table WHERE priority=:priorityName")
    fun filterByPriority(priorityName: String): Observable<List<NoteEntity>>

}