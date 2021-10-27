package com.example.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Update
    fun update(taskModel: TaskModel)

    @Delete
    fun delete(taskModel: TaskModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(taskModel: TaskModel)

    @Query("SELECT * FROM tableTask WHERE comleted=:comleted")
    fun taskAll(comleted: Boolean): LiveData<List<TaskModel>>

}