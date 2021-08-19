package com.pat.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pat.todoapp.R
import com.pat.todoapp.adapters.RecyclerAdapter
import com.pat.todoapp.databinding.FragmentMainBinding
import com.pat.todoapp.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MainFragment : Fragment() {

    private val mainViewModel by sharedViewModel<MainViewModel>()
    private lateinit var recyclerView: RecyclerView
    private val adapter by lazy { RecyclerAdapter(mutableListOf()) }
    private lateinit var binding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        recyclerView.adapter = adapter
        updateUI()

    }

    override fun onStart() {
        super.onStart()

        binding.addTodoImageView.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_newTodoFragment)
        }
    }


    private fun updateUI() {
        mainViewModel.todoList.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
        })
    }

}
