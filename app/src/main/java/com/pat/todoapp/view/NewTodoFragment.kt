package com.pat.todoapp.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.annotation.RequiresApi
import com.pat.todoapp.R
import com.pat.todoapp.databinding.FragmentNewTodoBinding
import com.pat.todoapp.viewmodel.MainViewModel
import com.pat.todoapp.viewmodel.MainViewModel.MainAction.RefreshList
import com.pat.todoapp.viewmodel.MainViewModel.MainAction.SaveTodo
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class NewTodoFragment : Fragment() {

    private val mainViewModel by sharedViewModel<MainViewModel>()
    private lateinit var binding: FragmentNewTodoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewTodoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.saveTodoButton.setOnClickListener {

            val todoDescription = binding.todoDescriptionEditText.text.toString()
            val todoDate = binding.todoDateEditText.text.toString()
            mainViewModel.action.trySend(SaveTodo(todoDescription, todoDate, resources.getString(R.string.job)))

        }
    }
}