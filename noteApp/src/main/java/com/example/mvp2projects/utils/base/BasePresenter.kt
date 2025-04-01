package com.example.mvpprojects.utils.base

import io.reactivex.rxjava3.disposables.Disposable

open class BasePresenter : BaseContract.Presenter {

     var disposable: Disposable? = null

    override fun dispose() {


        this.disposable!!.dispose()

    }


}