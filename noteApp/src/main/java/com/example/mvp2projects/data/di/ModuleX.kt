package com.example.mvp2projects.data.di



import androidx.fragment.app.Fragment
import com.example.mvp2projects.ui.addNoteFragment.AddNoteContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent



@Module
@InstallIn(FragmentComponent::class)
class ModuleX {

    @Provides
    fun provideView(fragment : Fragment) : AddNoteContract.View{
        return fragment as AddNoteContract.View
    }





}