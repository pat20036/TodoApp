package com.pat.todoapp.viewmodel

import androidx.lifecycle.*
import com.pat.todoapp.model.TodoItem
import com.pat.todoapp.room.TodoRoomRepository
import com.pat.todoapp.viewmodel.MainViewModel.MainAction.RefreshList
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
                   is SaveTodo -> todoRoomRepository.addNewTodo(todoItem = TodoItem(0, it.todoDescription, it.todoDate, it.todoCategory))
                    RefreshList -> todoRoomRepository.getAllTodo()
                }
            }

        }
    }

    sealed class MainAction {
        data class SaveTodo(val todoDescription: String, val todoDate: String, val todoCategory: String) : MainAction()
        object RefreshList: MainAction()
    }
}