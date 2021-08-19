package com.pat.todoapp.viewmodel

import androidx.lifecycle.*
import com.pat.todoapp.DataValidator
import com.pat.todoapp.model.TodoItem
import com.pat.todoapp.room.TodoRoomRepository
import com.pat.todoapp.viewmodel.MainAction.RefreshTaskList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(private val todoRoomRepository: TodoRoomRepository) : ViewModel() {

    val actions = Channel<MainAction>()

    private val _todoList = MutableLiveData<List<TodoItem>>()
    val todoList: LiveData<List<TodoItem>> get() = _todoList

    private val _dataValidationError = MutableLiveData<Boolean>()
    val dataValidationError: LiveData<Boolean> get() = _dataValidationError

    init {

        viewModelScope.launch {
            _todoList.value = todoRoomRepository.getAllTodo()
        }
        viewModelScope.launch {
            actions.receiveAsFlow().collect { action ->
                when (action) {

                    is MainAction.AddNewTask -> {
                        if (DataValidator.isValidTaskData(
                                action.description,
                                action.date,
                                action.category
                            )
                        ) {
                            todoRoomRepository.addNewTodo(
                                todoItem = TodoItem(
                                    0,
                                    action.description,
                                    action.date,
                                    action.category
                                )
                            )

                        } else {
                            _dataValidationError.value = true

                        }
                    }

                    RefreshTaskList -> _todoList.value = todoRoomRepository.getAllTodo()
                }
            }

        }
    }
}

sealed class MainAction {
    data class AddNewTask(
        val description: String,
        val date: String,
        val category: String
    ) : MainAction()

    object RefreshTaskList : MainAction()
}