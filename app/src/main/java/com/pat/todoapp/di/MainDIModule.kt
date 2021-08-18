package com.pat.todoapp.di

import com.pat.todoapp.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modules = module {
    viewModel { MainViewModel() }
}