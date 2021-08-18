package com.pat.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val action = Channel<MainAction>()

    init {
        viewModelScope.launch {

        }
    }

    sealed class MainAction
    {

    }
}