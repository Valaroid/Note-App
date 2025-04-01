package com.example.mvp2projects.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mvp2projects.utils.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = Constants.NOTE_TABLE)
data class NoteEntity (
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var title:String="",
    var description: String="",
    var priority: String="",
    var category : String=""
) : Parcelable

