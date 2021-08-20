package com.pat.todoapp.utils

object DataValidator {

    fun isValidTaskData(description: String, date: String, category: String): Boolean =
        validateDescription(description) && validateDate(date) && isCategorySelected(category)

    private fun validateDescription(description: String): Boolean = description.isNotBlank()

    private fun validateDate(date: String): Boolean = date.isNotBlank()

    private fun isCategorySelected(category: String): Boolean = category.isNotBlank()
}
