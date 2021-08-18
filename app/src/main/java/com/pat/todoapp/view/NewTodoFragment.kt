package com.pat.todoapp.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.pat.todoapp.R
import com.pat.todoapp.databinding.FragmentNewTodoBinding

class NewTodoFragment : Fragment() {

    private lateinit var binding: FragmentNewTodoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewTodoBinding.inflate(layoutInflater)
        return binding.root
    }
}