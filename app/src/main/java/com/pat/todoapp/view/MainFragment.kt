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
import com.pat.todoapp.viewmodel.MainViewModel.MainAction.RefreshList
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

    override fun onResume() {
        super.onResume()

        mainViewModel.action.trySend(RefreshList)
    }

    private fun updateUI() {
        mainViewModel.todoList.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) showInfo()
            else hideInfo()

            hideProgressBar()
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
