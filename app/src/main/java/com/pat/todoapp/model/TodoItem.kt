package com.pat.todoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pat.todoapp.utils.TASK_LIST_TABLE_NAME


@Entity(tableName = TASK_LIST_TABLE_NAME)
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val todoDescription: String,
    val todoDate: String,
    val todoCategory: String
)
