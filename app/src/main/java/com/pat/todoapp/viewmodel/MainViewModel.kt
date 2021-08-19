package com.pat.todoapp.viewmodel

import androidx.lifecycle.*
import com.pat.todoapp.model.TodoItem
import com.pat.todoapp.room.TodoRoomRepository
import com.pat.todoapp.viewmodel.MainViewModel.MainAction.SaveTodo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(private val todoRoomRepository: TodoRoomRepository) : ViewModel() {

    val action = Channel<MainAction>()

    private val _todoList = MutableLiveData<List<TodoItem>>()
    val todoList: LiveData<List<TodoItem>> get() = _todoList

    init {

        viewModelScope.launch {
           _todoList.value = todoRoomRepository.getAllTodo()
        }
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