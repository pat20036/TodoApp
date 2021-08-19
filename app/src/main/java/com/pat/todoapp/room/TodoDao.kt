package com.pat.todoapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.pat.todoapp.model.TodoItem

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewTodo(todoItem: TodoItem)
}