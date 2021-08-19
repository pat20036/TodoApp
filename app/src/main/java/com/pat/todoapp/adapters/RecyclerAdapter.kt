package com.pat.todoapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pat.todoapp.R
import com.pat.todoapp.model.TodoItem

class RecyclerAdapter(private val todoList: MutableList<TodoItem>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = todoList.size


    fun updateList(todo: List<TodoItem>) {
        todoList.apply {
            clear()
            addAll(todo)
        }
        notifyDataSetChanged()
    }

}