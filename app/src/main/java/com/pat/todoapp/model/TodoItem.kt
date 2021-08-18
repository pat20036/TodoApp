package com.pat.todoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class TodoItem(
    @PrimaryKey val id: Int,
    val todoDescription: String,
    val todoDate: String,
    val todoCategory: String
)
