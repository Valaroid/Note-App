package com.example.mvp2projects.ui.notesActivity

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mvp2projects.R
import com.example.mvp2projects.data.model.NoteEntity
import com.example.mvp2projects.databinding.ActivityNotesBinding
import com.example.mvp2projects.ui.adapter.NotesAdapter
import com.example.mvp2projects.ui.addNoteFragment.AddNoteFragment
import com.example.mvp2projects.utils.Constants
import com.example.mvp2projects.utils.Constants.HIGH
import com.example.mvp2projects.utils.Constants.KEY_BUNDLE
import com.example.mvp2projects.utils.Constants.KEY_EDIT
import com.example.mvp2projects.utils.Constants.LOW
import com.example.mvp2projects.utils.Constants.MEDIUM
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class NotesActivity : AppCompatActivity(), NotesContract.View {
    //binding
    private lateinit var binding: ActivityNotesBinding

    //adapter
    @Inject
    lateinit var noteAdapter: NotesAdapter

    @Inject
    lateinit var presenter: NotesPresenter

    //filter item in toolBar
    var selectedPriorityItem = ""
    var selectedPriorityIndex = 0
    val priorityArray = arrayOf(Constants.ALL,HIGH, MEDIUM, LOW)
    var selectedPriority = priorityArray[selectedPriorityIndex]


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //for list Notes
        presenter.getAllNotes()

        binding.apply {

            //set toolbar
            setSupportActionBar(toolBarNotes)

            //for add note
            btnAddNotes.setOnClickListener {
                AddNoteFragment().show(supportFragmentManager, "AddNoteFragment().tag")

            }

            //click on item note from adapter
            noteAdapter.clickOnDots { itemNote, view, editState ->

                val popMenu = PopupMenu(this@NotesActivity, view, Gravity.CENTER)
                popMenu.inflate(R.menu.menu_popup_notes)
                popMenu.show()

                popMenu.setOnMenuItemClickListener {

                    when (it.itemId) {

                        //for edit note
                        R.id.editNote_notes -> {
                            val bundle = Bundle()
                            bundle.putInt(KEY_BUNDLE, itemNote.id)
                            bundle.putString(KEY_EDIT, editState)
                            val frag = AddNoteFragment()
                            frag.arguments = bundle
                            frag.show(supportFragmentManager, frag.tag)

                        }


                        //for delete note
                        R.id.deleteNote_notes -> {

                            presenter.deleteNote(itemNote)
                        }


                    }

                    true


                }


            }


        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)

        // For searchView in toolbar
        val searchItem = menu.findItem(R.id.search_toolBar)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Search"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //presenter search

                presenter.searchNotesByName(newText.toString())



                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.filter_toolBar) {
            //for filter item in toolbar
            buildAlertDialogForFilterItem()

        }

        return super.onOptionsItemSelected(item)
    }

    private fun buildAlertDialogForFilterItem() {


        //var selectedPriorityIndex = 0



        val dialog= AlertDialog.Builder(this)
            .setTitle("Choose Your Priority")
            .setSingleChoiceItems(priorityArray, selectedPriorityIndex) { dialog, which ->

                selectedPriorityIndex = which
                selectedPriority = priorityArray[which]
                //now we can send the selected priority for db with presenter we use selected priority
                if (selectedPriority== Constants.ALL){
                    selectedPriorityIndex=0
                    presenter.getAllNotes()
                }else{
                    priorityArray.forEach {
                        if(it==selectedPriority){
                            selectedPriorityIndex=priorityArray.indexOf(it)
                        }
                    }
                    presenter.filterByPriority(selectedPriority)
                }

                dialog.dismiss()

            }.show()




    }

    override fun showGetAllNotes(list: List<NoteEntity>) {
        binding.emptyLayOutNotes.visibility = View.GONE
        binding.recyclerNotes.visibility = View.VISIBLE
        //build recycler
        binding.recyclerNotes.apply {

            noteAdapter.setList(list)

            adapter = noteAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        }


    }

    override fun showDeleteNote(message: String) {
        Toast.makeText(this@NotesActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showsSearchNotesByName(list: List<NoteEntity>) {
        binding.emptyLayOutNotes.visibility = View.GONE
        binding.recyclerNotes.visibility = View.VISIBLE

        binding.recyclerNotes.apply {
            noteAdapter.setList(list)

            adapter = noteAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }

    override fun showEmpty() {
        binding.apply {
            emptyLayOutNotes.visibility = View.VISIBLE
            recyclerNotes.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.closeObservable()
    }




}