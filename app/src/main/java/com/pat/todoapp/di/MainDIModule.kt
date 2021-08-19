package com.pat.todoapp.di

import com.pat.todoapp.room.TodoDao
import com.pat.todoapp.room.TodoDatabase
import com.pat.todoapp.room.TodoRoomRepository
import com.pat.todoapp.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}

val repositoryModule = module {
    single { TodoRoomRepository(get()) }
}

val daoModule = module {
    single { TodoDatabase.getInstance(androidContext()).todoDao }
}

