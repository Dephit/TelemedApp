package com.app.telemed.models

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import java.io.Serializable
import java.lang.reflect.Type
import java.util.*

@Entity
data class GoalResponse(
    @PrimaryKey val id: Int = 0,
    var data: List<GoalData> = listOf(),
    var status: String = ""
): Serializable

class GoalResponseConverter {

    @TypeConverter
    fun stringToObjectList(d: String?): MutableList<GoalData>? {
        val data = d ?: return Collections.emptyList()
        return try {
            Gson().fromJson(data, object : TypeToken<List<GoalData>>() {}.type)
        }catch (e:Exception){
            Collections.emptyList()
        }
    }

    @TypeConverter
    fun objectListToString(someObjects: MutableList<GoalData>?): String? {
        return Gson().toJson(someObjects)
    }

}

@Dao
interface GoalResponseDao {
    @Query("SELECT * FROM GoalResponse")
    fun get(): GoalResponse?

    @Update
    fun update(profile: GoalResponse)

    @Insert
    fun insert(profile: GoalResponse)

    @Delete
    fun delete(profile: GoalResponse)
}

