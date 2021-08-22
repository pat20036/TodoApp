package com.pat.todoapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pat.todoapp.model.TodoItem
import com.pat.todoapp.utils.TASK_LIST_TABLE_NAME

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewTodo(todoItem: TodoItem): Long

    @Query("SELECT * FROM $TASK_LIST_TABLE_NAME ORDER BY id DESC")
    suspend fun getAllNotes(): List<TodoItem>
}