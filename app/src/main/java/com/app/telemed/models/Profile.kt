package com.app.telemed.models

import androidx.room.*
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import java.io.Serializable

@Entity
data class Profile(
        @PrimaryKey val id: Int = 0,
        var data: ProfileData = ProfileData(),
        var status: String = ""
): Serializable

class ProfileDataConverter {
    @TypeConverter
    fun fromTimestamp(value: String?): ProfileData? {
        return Gson().fromJson(value, ProfileData::class.java)
    }

    @TypeConverter
    fun dateToTimestamp(date: ProfileData?): String? {
        return Gson().toJson(date)
    }
}

@Dao
interface ProfileDao {
    @Query("SELECT * FROM Profile")
    fun get(): Flow<Profile?>

    @Update
    fun update(profile: Profile)

    @Insert
    fun insert(profile: Profile)

    @Delete
    fun delete(profile: Profile)
}