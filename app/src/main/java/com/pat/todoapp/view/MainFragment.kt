package com.pat.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.pat.todoapp.R
import com.pat.todoapp.databinding.FragmentMainBinding
import com.pat.todoapp.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MainFragment : Fragment() {

    private val mainViewModel by sharedViewModel<MainViewModel>()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.addTodoImageView.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_newTodoFragment)
        }
    }


}
