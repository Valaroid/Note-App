package com.example.mvp2projects.ui.notesActivity

import android.annotation.SuppressLint
import com.example.mvp2projects.data.model.NoteEntity
import com.example.mvp2projects.data.repository.AddNoteRepository
import com.example.mvpprojects.utils.base.BasePresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NotesPresenter @Inject constructor(
    private val view: NotesContract.View,
    private val repository: AddNoteRepository
) : BasePresenter(), NotesContract.Presenter {


    override fun getAllNotes() {
        disposable = repository.getAllNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty()) {
                    view.showGetAllNotes(it)
                } else {
                    view.showEmpty()
                }

            }

    }

    override fun deleteNote(note: NoteEntity) {
        disposable = repository.deleteNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.showDeleteNote("Note Deleted")
            }

    }

    @SuppressLint("CheckResult")
    override fun searchNotesByName(titleNote: String) {
        disposable = repository.searchNoteByName(titleNote)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty()) {
                    view.showsSearchNotesByName(it)
                } else {
                    view.showEmpty()
                }

            }
    }

    override fun filterByPriority(priorityName: String) {
        disposable=repository.filterByPriority(priorityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty()){
                    view.showGetAllNotes(it)
                }else{
                    view.showEmpty()
                }
            }
    }


    fun closeObservable() {
        dispose()
    }


}