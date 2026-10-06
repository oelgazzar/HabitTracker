package com.example.habittracker.di

import android.content.Context
import androidx.room3.Room
import com.example.habittracker.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "habit_tracker"
        )
            .createFromAsset("database/habit_tracker.db")
//            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesHabitDao(database: AppDatabase) = database.habitDao()
}
