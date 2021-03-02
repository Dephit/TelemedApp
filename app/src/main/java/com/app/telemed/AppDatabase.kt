package com.app.telemed

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.telemed.models.*

@Database(entities = [LoginResponse::class, Profile::class, GoalResponse::class], version = 2)
@TypeConverters(ProfileDataConverter::class, GoalResponseConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun loginDao(): LoginResponseDao
    abstract fun profileDao(): ProfileDao
    abstract fun goalResponseDao(): GoalResponseDao
}