package com.example.mvp2projects.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvp2projects.R
import com.example.mvp2projects.data.model.NoteEntity
import com.example.mvp2projects.databinding.ItemRecyclerNoteNotesBinding
import com.example.mvp2projects.utils.Constants.EDIT
import com.example.mvp2projects.utils.Constants.EDUCATION
import com.example.mvp2projects.utils.Constants.HEALTH
import com.example.mvp2projects.utils.Constants.HIGH
import com.example.mvp2projects.utils.Constants.HOME
import com.example.mvp2projects.utils.Constants.LOW
import com.example.mvp2projects.utils.Constants.MEDIUM
import com.example.mvp2projects.utils.Constants.WORK
import javax.inject.Inject

class NotesAdapter @Inject constructor() : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
   //binding
    private lateinit var binding: ItemRecyclerNoteNotesBinding

    //context
   private lateinit var context :Context

    //for img Dots
    private lateinit var click : (note: NoteEntity, View, String)->Unit
    fun clickOnDots(click:(NoteEntity, View, String)-> Unit){
        this.click=click
    }




    inner class NoteViewHolder(): RecyclerView.ViewHolder(binding.root){

        fun bindData(itemNote: NoteEntity){

            binding.apply {

                //for title
                txtTitleItemRecyclerNotes.text=itemNote.title

                //for description
                txtDescriptionItemRecyclerNotes.text=itemNote.description

                // for img category
                when(itemNote.category){

                    WORK->{ imgCategoryItemRecyclerNotes.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.work
                    ))}

                    HOME->{ imgCategoryItemRecyclerNotes.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.home))}

                    EDUCATION->{imgCategoryItemRecyclerNotes.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.education ))}

                    HEALTH->{ imgCategoryItemRecyclerNotes.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.healthcare))}


                }

                // for color priority
                when(itemNote.priority){

                    HIGH->{ viewColorPriorityItemRecyclerNotes.setBackgroundColor(ContextCompat.getColor(context,R.color.red))}

                    MEDIUM->{ viewColorPriorityItemRecyclerNotes.setBackgroundColor(ContextCompat.getColor(context,R.color.aqua))}

                    LOW->{ viewColorPriorityItemRecyclerNotes.setBackgroundColor(ContextCompat.getColor(context,R.color.green))}




                }

                //for img dots
                imgDotsItemRecyclerNotes.setOnClickListener {
                    click(itemNote,it, EDIT)

                }


            }

        }




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        context=parent.context
        val inflater=LayoutInflater.from(context)
        binding=ItemRecyclerNoteNotesBinding.inflate(inflater,parent,false)
        return NoteViewHolder()
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindData(differ.currentList[position])
    }


    //------------------------------diffUtil---------------------------------//

    private val diffUtil =object :DiffUtil.ItemCallback<NoteEntity>(){

        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
           return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem==newItem
        }

    }

    private val differ = AsyncListDiffer(this,diffUtil)

    fun setList(listNotes:List<NoteEntity>){
        differ.submitList(listNotes)
    }



}