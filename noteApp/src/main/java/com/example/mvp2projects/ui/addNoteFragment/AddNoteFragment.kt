package com.example.mvp2projects.ui.addNoteFragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.mvp2projects.data.model.NoteEntity
import com.example.mvp2projects.databinding.FragmentAddNoteBinding
import com.example.mvp2projects.utils.Constants.EDIT
import com.example.mvp2projects.utils.Constants.EDUCATION
import com.example.mvp2projects.utils.Constants.HEALTH
import com.example.mvp2projects.utils.Constants.HIGH
import com.example.mvp2projects.utils.Constants.HOME
import com.example.mvp2projects.utils.Constants.KEY_BUNDLE
import com.example.mvp2projects.utils.Constants.KEY_EDIT
import com.example.mvp2projects.utils.Constants.LOW
import com.example.mvp2projects.utils.Constants.MEDIUM
import com.example.mvp2projects.utils.Constants.WORK
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AddNoteFragment : BottomSheetDialogFragment(), AddNoteContract.View {
    //binding
    private lateinit var binding: FragmentAddNoteBinding

    //category
    private lateinit var category: String

    //priority
    private lateinit var priority: String

    private lateinit var arrayOfCategory: Array<String>
    private lateinit var arrayOfPriority: Array<String>

    @Inject
    lateinit var note: NoteEntity


    @Inject
    lateinit var presenter: AddNotePresenter


    //for edit note
    var idUpdateNote: Int = 0
    lateinit var state: String

    //val presenter = AddNotePresenter(this,repository)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        createSpinners()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {


            //for img close
            imgCloseAddNote.setOnClickListener {
                dismiss()
            }



            state = arguments?.getString(KEY_EDIT).toString()

            if (state == EDIT) {
                //this  edit note time
                idUpdateNote = arguments?.getInt(KEY_BUNDLE)!!
                presenter.getNoteById(idUpdateNote)

                //for button update note in db
                btnSaveAddNote.setOnClickListener {
                    val title = edtTitleAddNote.text.toString()
                    val dec = edtDescriptionAddNote.text.toString()
                    if (title.isNotEmpty() || dec.isNotEmpty()) {

                        note.id = idUpdateNote
                        note.title = title
                        note.description = dec
                        note.category = category
                        note.priority = priority

                        presenter.updateNote(note)
                        dismiss()


                    }


                }

            } else {
                //this is for add note state

                //for spinner category
               categorySpinnerHandle()

                //for spinner priority
                prioritySpinnerHandle()


                //for button save and add to db
                btnSaveAddNote.setOnClickListener {
                    val title = edtTitleAddNote.text.toString()
                    val dec = edtDescriptionAddNote.text.toString()
                    if (title.isNotEmpty() || dec.isNotEmpty()) {
                        note.id = 0
                        note.title = title
                        note.description = dec
                        note.category = category
                        note.priority = priority

                        presenter.addNoteToDb(note)
                        dismiss()


                    }


                }

            }




        }


    }

    override fun showAddNoteToDb(successMessage: String) {
        Toast.makeText(requireContext(), successMessage, Toast.LENGTH_SHORT).show()
    }

    override fun showGetNoteById(note: NoteEntity) {
        binding.apply {
            edtTitleAddNote.setText(note.title)
            edtDescriptionAddNote.setText(note.description)

            spinnerCategoryAddNote.setSelection(getIndexFromSpinner(arrayOfCategory,note.category))
            spinnerPriorityAddNOte.setSelection(getIndexFromSpinner(arrayOfPriority,note.priority))

            category=note.category
            priority=note.priority


            //for spinner category
            categorySpinnerHandle()


            //for spinner priority
           prioritySpinnerHandle()
        }
    }


    private fun getIndexFromSpinner(array: Array<String>,item:String) : Int {
        var index = 0
        for (i in array.indices){
            if (array[i]==item){
                index=i
                break
            }
        }
        return index
    }

    override fun showUpdateNote(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    //build spinner
    private fun createSpinners() {

        //for build category spinner
        arrayOfCategory = arrayOf(WORK, HOME, EDUCATION, HEALTH)
        val adapterForCategory =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayOfCategory)
        adapterForCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategoryAddNote.adapter = adapterForCategory

        //for priority spinner
        arrayOfPriority = arrayOf(HIGH, MEDIUM, LOW)
        val adapterForPriority =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayOfPriority)
        adapterForPriority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPriorityAddNOte.adapter = adapterForPriority


    }


    private fun categorySpinnerHandle(){
        binding.spinnerCategoryAddNote.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                category = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })
    }


    private fun prioritySpinnerHandle(){
        binding.spinnerPriorityAddNOte.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                priority = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {


            }
        })
    }

}