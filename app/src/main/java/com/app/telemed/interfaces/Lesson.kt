package com.app.telemed.interfaces

import android.os.Parcel
import android.os.Parcelable
import com.app.telemed.databinding.DayViewBinding
import java.util.*

data class Lesson(
    var date: Calendar = Calendar.getInstance(),
    var passed: Int = 0,
    var isSighned: Boolean = false,
    var title: String? = "",
    val desc: String? = "",
) : Parcelable {
    constructor(parcel: Parcel) : this(
        Calendar.getInstance().fromLong(parcel.readLong()),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(date.time.time)
        parcel.writeInt(passed)
        parcel.writeByte(if (isSighned) 1 else 0)
        parcel.writeString(title)
        parcel.writeString(desc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Lesson> {
        override fun createFromParcel(parcel: Parcel): Lesson {
            return Lesson(parcel)
        }

        override fun newArray(size: Int): Array<Lesson?> {
            return arrayOfNulls(size)
        }
    }

    fun isSoon() = passed == 0

    fun isPassed() = passed == 1

    fun isNotSoon() = passed == 2
}

fun Calendar.fromLong(long: Long): Calendar {
    val cal = Calendar.getInstance()
    cal.time.time = long
    return cal
}
