package com.example.todocompose.core.data.local

import androidx.room.*
import com.example.todocompose.core.domain.model.TodoTask

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo_table ORDER BY id DESC")
    suspend fun getAllTasks(): List<TodoTask>

    @Query("SELECT * FROM todo_table WHERE id=:taskId")
    suspend fun getSelectedTask(taskId: Int): TodoTask

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(toDoTask: TodoTask)

    @Delete
    suspend fun deleteTask(toDoTask: TodoTask)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery OR description LIKE :searchQuery")
    suspend fun searchDatabase(searchQuery: String): List<TodoTask>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    suspend fun sortByLowPriority(): List<TodoTask>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    suspend fun sortByHighPriority(): List<TodoTask>

}