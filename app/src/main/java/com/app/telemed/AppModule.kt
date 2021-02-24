package com.app.telemed

import android.content.Context
import androidx.room.Room
import com.app.telemed.models.LoginResponseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(app, AppDatabase::class.java, "telemed_db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideChannelDao(appDatabase: AppDatabase): LoginResponseDao {
        return appDatabase.loginDao()
    }
}