package com.example.todolist.database

import android.content.Context
import androidx.room.Room

private const val dbName= "TodoListMoyez4646.db"

object DatabaseClient {
    fun getService(context: Context): DatabaseService{
        return Room.databaseBuilder(
            context,
            DatabaseService::class.java,
            dbName
        ).build()
    }
}