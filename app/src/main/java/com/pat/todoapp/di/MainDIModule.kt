package com.pat.todoapp.di

import android.app.Application
import androidx.room.Room
import com.pat.todoapp.room.TodoDao
import com.pat.todoapp.room.TodoDatabase
import com.pat.todoapp.room.TodoRoomRepository
import com.pat.todoapp.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}

val repositoryModule = module {
    single { TodoRoomRepository(get()) }
}

val databaseModule = module {

    fun provideDatabase(application: Application): TodoDatabase {
        return Room.databaseBuilder(application, TodoDatabase::class.java, "todo_table")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideTodoDao(database: TodoDatabase): TodoDao {
        return  database.todoDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideTodoDao(get()) }
}

