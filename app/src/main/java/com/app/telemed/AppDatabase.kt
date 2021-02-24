package com.app.telemed

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.telemed.models.LoginResponse
import com.app.telemed.models.LoginResponseDao

@Database(entities = [LoginResponse::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun loginDao(): LoginResponseDao
}