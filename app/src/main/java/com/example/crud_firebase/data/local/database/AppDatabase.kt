package com.example.crud_firebase.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.crud_firebase.data.local.dao.TaskDraftDao
import com.example.crud_firebase.data.local.entity.TaskDraftEntity

@Database(entities = [TaskDraftEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDraftDao(): TaskDraftDao
}