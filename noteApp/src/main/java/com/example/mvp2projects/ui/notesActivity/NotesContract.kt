package com.example.mvp2projects.ui.notesActivity

import com.example.mvp2projects.data.model.NoteEntity
import com.example.mvpprojects.utils.base.BaseContract

interface NotesContract {

    interface View : BaseContract.View {

        fun showGetAllNotes(list:List<NoteEntity>)
        fun showDeleteNote(message : String)
        fun showsSearchNotesByName(list: List<NoteEntity>)

    }



    interface Presenter{

        fun getAllNotes()
        fun deleteNote(note: NoteEntity)
        fun searchNotesByName(titleNote:String)
        fun filterByPriority(priorityName: String)

    }


}