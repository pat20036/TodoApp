package com.pat.todoapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pat.todoapp.model.TodoItem

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewTodo(todoItem: TodoItem): Long

    @Query("SELECT * FROM todo_table")
    suspend fun getAllNotes(): List<TodoItem>
}