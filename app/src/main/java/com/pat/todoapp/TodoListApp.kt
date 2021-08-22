package com.pat.todoapp

import android.app.Application
import com.pat.todoapp.di.databaseModule
import com.pat.todoapp.di.repositoryModule
import com.pat.todoapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TodoListApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@TodoListApp)
            modules(viewModelModule, repositoryModule, databaseModule)
        }
    }
}