package com.example.mvp2projects.data.repository

import com.example.mvp2projects.data.db.NoteDao
import com.example.mvp2projects.data.model.NoteEntity
import javax.inject.Inject


class AddNoteRepository @Inject constructor (val dao : NoteDao) {

    fun addNote(note : NoteEntity) = dao.addNote(note)

    fun getAllNotes()=dao.getAllNotes()

    fun deleteNote(note: NoteEntity)=dao.deleteNote(note)

    fun getNoteById(id:Int)=dao.getNoteById(id)

    fun updateNote(note: NoteEntity)=dao.updateNote(note)

    fun searchNoteByName(titleNote:String)=dao.searchNoteByName(titleNote)

    fun filterByPriority(priorityName: String)=dao.filterByPriority(priorityName)



}