package com.pat.todoapp.view

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_TEXT
import com.pat.todoapp.R
import com.pat.todoapp.databinding.FragmentNewTodoBinding
import com.pat.todoapp.viewmodel.MainAction
import com.pat.todoapp.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewTodoFragment : Fragment() {

    private val mainViewModel by viewModel<MainViewModel>()
    private lateinit var binding: FragmentNewTodoBinding

    private var todoDescription: String = ""
    private var todoDate: String = ""
    private var todoCategory: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewTodoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDropdownMenu()
        observeDataValidationError()
        observeIsDataAddedSuccessfully()


        binding.apply {
            saveTodoButton.setOnClickListener {
                todoDescription = binding.todoDescriptionEditText.text.toString()
                todoDate = binding.todoDateEditText.text.toString()
                todoCategory = binding.todoCategoryTextView.text.toString()

                sendAddNewTaskAction(todoDescription, todoDate, todoCategory)
            }
            cancelTodoButton.setOnClickListener {
                findNavController().popBackStack()
            }
            backArrowItem.setOnClickListener {
                findNavController().popBackStack()
            }
            todoDateEditText.setOnClickListener {
                showDatePicker()
            }
        }
    }

    private fun observeIsDataAddedSuccessfully() {
        mainViewModel.isDataAddedSuccessfully.observe(viewLifecycleOwner, Observer {
            if (it >= 0) {
                Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else showDialog()
        })
    }

    private fun sendAddNewTaskAction(
        todoDescription: String,
        todoDate: String,
        todoCategory: String
    ) {
        mainViewModel.actions.trySend(
            MainAction.AddNewTask(
                todoDescription,
                todoDate,
                todoCategory
            )
        )
    }

    private fun observeDataValidationError() {
        mainViewModel.dataValidationError.observe(viewLifecycleOwner, Observer {
            if (it) Toast.makeText(context, "Incorrect data!", Toast.LENGTH_LONG).show()
        })
    }

    private fun setupDropdownMenu() {
        val todoCategoriesList = resources.getStringArray(R.array.todo_categories)
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_item, todoCategoriesList)
        binding.todoCategoryTextView.setAdapter(arrayAdapter)
    }

    private fun showDialog() {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        builder?.apply {
            setMessage(R.string.an_error_occurred)
            setTitle(R.string.error)
            setPositiveButton(R.string.try_again,
                DialogInterface.OnClickListener { _, _ ->
                    sendAddNewTaskAction(todoDescription, todoDate, todoCategory)
                })
            setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })
        }
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()

    }

    private fun showDatePicker() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .build()

        datePicker.show(parentFragmentManager, "tag");

        datePicker.addOnPositiveButtonClickListener {
            binding.todoDateEditText.setText(datePicker.headerText)
        }
    }
}