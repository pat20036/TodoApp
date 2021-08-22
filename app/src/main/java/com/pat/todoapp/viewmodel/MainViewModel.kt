package com.pat.todoapp.viewmodel

import androidx.lifecycle.*
import com.pat.todoapp.utils.DataValidator
import com.pat.todoapp.model.TodoItem
import com.pat.todoapp.room.TodoRoomRepository
import com.pat.todoapp.viewmodel.MainAction.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(private val todoRoomRepository: TodoRoomRepository) : ViewModel() {

    val actions = Channel<MainAction>()
    val showDatePicker = Channel<Unit>()

    private val _todoList = MutableStateFlow<List<TodoItem>?>(null)
    val todoList: StateFlow<List<TodoItem>?> get() = _todoList

    private val _todoValidationError = MutableStateFlow(false)
    val todoValidatorError: StateFlow<Boolean> get() = _todoValidationError

    private val _isTodoAddedSuccessfully = MutableStateFlow<Boolean?>(null)
    val isTodoAddedSuccessfully: StateFlow<Boolean?> get() = _isTodoAddedSuccessfully

    private val currentTodoItem = MutableStateFlow<TodoItem?>(null)


    init {
        viewModelScope.launch { _todoList.value = todoRoomRepository.getAllTodo() }
        viewModelScope.launch {
            actions.receiveAsFlow().collect { action ->
                when (action) {
                    is AddNewTask -> {
                        if (DataValidator.isValidTaskData(action.description, action.date, action.category)) {

                            val currentItem = TodoItem(0, action.description, action.date, action.category)

                            val itemResult = todoRoomRepository.addNewTodo(currentItem)
                            currentTodoItem.value = currentItem
                            _isTodoAddedSuccessfully.value = itemResult >= 0

                        } else {
                            _todoValidationError.value = true
                        }
                    }

                    RefreshTaskList -> _todoList.value = todoRoomRepository.getAllTodo()

                    ShowDatePicker -> showDatePicker.trySend(Unit)

                    TryAddNewTaskAgain -> {
                        if (DataValidator.isValidTaskData(currentTodoItem.value!!.todoDescription, currentTodoItem.value!!.todoDate, currentTodoItem.value!!.todoCategory)) {
                            val result = todoRoomRepository.addNewTodo(currentTodoItem.value!!)
                            if (result >= 0) {
                                _isTodoAddedSuccessfully.value = true
                            }
                        } else {
                            _todoValidationError.value = true
                        }

                    }
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

    object TryAddNewTaskAgain : MainAction()
    object RefreshTaskList : MainAction()
    object ShowDatePicker : MainAction()
}

