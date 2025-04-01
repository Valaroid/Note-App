package com.example.mvp2projects.data.di

import android.app.Activity
import com.example.mvp2projects.ui.notesActivity.NotesContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class ModuleAcDependency {

    @Provides
    fun provideActivityView(activity : Activity) : NotesContract.View{
        return activity as NotesContract.View
    }

}