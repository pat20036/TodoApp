package com.pat.todoapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pat.todoapp.model.TodoItem

@Database(
    entities = [TodoItem::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase : RoomDatabase() {
    abstract val todoDao: TodoDao
}