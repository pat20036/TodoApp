package com.pat.todoapp.room

import com.pat.todoapp.model.TodoItem

class TodoRoomRepository(private val todoDao: TodoDao) {

    suspend fun addNewTodo(todoItem: TodoItem) = todoDao.addNewTodo(todoItem)

    suspend fun getAllTodo(): List<TodoItem> = todoDao.getAllNotes()
}