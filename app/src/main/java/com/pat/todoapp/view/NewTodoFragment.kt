package com.pat.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.pat.todoapp.R
import com.pat.todoapp.databinding.FragmentNewTodoBinding
import com.pat.todoapp.viewmodel.MainAction
import com.pat.todoapp.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewTodoFragment : Fragment() {

    private val mainViewModel by viewModel<MainViewModel>()
    private lateinit var binding: FragmentNewTodoBinding

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

        binding.saveTodoButton.setOnClickListener {
            val todoDescription = binding.todoDescriptionEditText.text.toString()
            val todoDate = binding.todoDateEditText.text.toString()
            val todoCategory = binding.todoCategoryTextView.text.toString()
            mainViewModel.actions.trySend(
                MainAction.AddNewTask(
                    todoDescription,
                    todoDate,
                    todoCategory
                )
            )
        }

        binding.cancelTodoButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeIsDataAddedSuccessfully() {
        mainViewModel.isDataAddedSuccessfully.observe(viewLifecycleOwner, Observer {
            if (it >= 0) {
                Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {

            }
        })
    }

    private fun observeDataValidationError() {
        mainViewModel.dataValidationError.observe(viewLifecycleOwner, Observer {
            if (it) Toast.makeText(context, "Invalid data!", Toast.LENGTH_LONG).show()
        })
    }

    private fun setupDropdownMenu() {
        val todoCategoriesList = resources.getStringArray(R.array.todo_categories)
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_item, todoCategoriesList)
        binding.todoCategoryTextView.setAdapter(arrayAdapter)
    }

}