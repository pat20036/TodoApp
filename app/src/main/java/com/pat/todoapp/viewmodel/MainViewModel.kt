package com.pat.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pat.todoapp.model.TodoItem
import com.pat.todoapp.room.TodoRoomRepository
import com.pat.todoapp.viewmodel.MainViewModel.MainAction.SaveTodo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(private val todoRoomRepository: TodoRoomRepository) : ViewModel() {

    val action = Channel<MainAction>()

    init {
        viewModelScope.launch {
            action.receiveAsFlow().collect {
                when (it) {
                    SaveTodo -> todoRoomRepository.addNewTodo(todoItem = TodoItem(0, "1", "1", "1"))
                }
            }

        }
    }

    sealed class MainAction {
        object SaveTodo : MainAction()
    }
}