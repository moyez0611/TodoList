package com.example.todolist.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tableTask")
data class TaskModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var task: String,
    var comleted: Boolean,
    var date: Long,
) : Serializable
