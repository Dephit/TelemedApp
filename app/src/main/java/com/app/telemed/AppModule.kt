package com.app.telemed

import android.content.Context
import androidx.room.Room
import com.app.telemed.models.LoginResponseDao
import com.app.telemed.models.ProfileDao
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
    fun loginResponseDao(appDatabase: AppDatabase): LoginResponseDao {
        return appDatabase.loginDao()
    }

    @Provides
    fun profileDao(appDatabase: AppDatabase): ProfileDao {
        return appDatabase.profileDao()
    }


}