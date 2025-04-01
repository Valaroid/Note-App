package com.example.mvpprojects.utils.base



interface BaseContract {

    interface View{
        fun showEmpty()

    }


    interface Presenter{
        fun dispose()

    }


}