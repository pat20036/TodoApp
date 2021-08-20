package com.pat.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pat.todoapp.R
import com.pat.todoapp.adapters.RecyclerAdapter
import com.pat.todoapp.databinding.FragmentMainBinding
import com.pat.todoapp.viewmodel.MainAction
import com.pat.todoapp.viewmodel.MainAction.RefreshTaskList
import com.pat.todoapp.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {

    private val mainViewModel by viewModel<MainViewModel>()
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

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        updateUI()

        binding.addTodoFloatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_newTodoFragment)
        }
    }

    override fun onResume() {
        super.onResume()

        mainViewModel.actions.trySend(RefreshTaskList)
    }

    private fun updateUI() {
        mainViewModel.todoList.observe(viewLifecycleOwner, Observer {
            hideProgressBar()
            if (it.isEmpty()) showInfo()
            else hideInfo()

            adapter.updateList(it)
        })
    }

    private fun showInfo() {
        binding.emptyListInfoTextView.visibility = View.VISIBLE
        hideProgressBar()
    }

    private fun hideInfo() {
        binding.emptyListInfoTextView.visibility = View.GONE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

}
