package com.example.mvp2projects.ui.addNoteFragment

import com.example.mvp2projects.data.model.NoteEntity

interface AddNoteContract {


    interface View {
        fun showAddNoteToDb(successMessage: String)
        fun showGetNoteById(note: NoteEntity)
        fun showUpdateNote(message: String)
    }


    interface Presenter {
        fun addNoteToDb(note: NoteEntity)
        fun getNoteById(id:Int)
        fun updateNote(note: NoteEntity)

    }


}