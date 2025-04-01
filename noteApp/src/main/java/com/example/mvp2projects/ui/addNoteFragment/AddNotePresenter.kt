package com.example.mvp2projects.ui.addNoteFragment

import android.annotation.SuppressLint
import com.example.mvp2projects.data.model.NoteEntity
import com.example.mvp2projects.data.repository.AddNoteRepository
import com.example.mvpprojects.utils.base.BasePresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class AddNotePresenter @Inject constructor (private val view: AddNoteContract.View, private val repository: AddNoteRepository) :
    BasePresenter(), AddNoteContract.Presenter {



    @SuppressLint("CheckResult")
    override fun addNoteToDb(note: NoteEntity) {

        disposable=repository.addNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.showAddNoteToDb("Note Added")
            }


    }

    override fun getNoteById(id: Int) {
        disposable=repository.getNoteById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                view.showGetNoteById(it)
            })
    }

    override fun updateNote(note: NoteEntity) {
        disposable=repository.updateNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.showUpdateNote("Note Updated")
            }
    }


}