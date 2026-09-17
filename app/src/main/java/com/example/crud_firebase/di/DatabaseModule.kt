package com.example.crud_firebase.di

import android.content.Context
import androidx.room.Room
import com.example.crud_firebase.data.local.dao.TaskDraftDao
import com.example.crud_firebase.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "task_manager_db"
        ).build()
    }

    @Provides
    fun provideTaskDraftDao(db: AppDatabase): TaskDraftDao {
        return db.taskDraftDao()
    }
}