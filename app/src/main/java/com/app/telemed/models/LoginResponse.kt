package com.app.telemed.models

import androidx.paging.DataSource
import androidx.room.*

@Entity
data class LoginResponse(
    @PrimaryKey(autoGenerate = false) var token: String = ""
)

@Dao
interface LoginResponseDao {
    @Query("SELECT * FROM LoginResponse")
    fun get(): LoginResponse?

    @Update
    fun update(code: LoginResponse)

    @Insert
    fun insert(code: LoginResponse)

    @Delete
    fun delete(code: LoginResponse)
}

