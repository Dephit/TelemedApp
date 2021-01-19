package com.app.telemed.interfaces

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Lesson(
    var date: Calendar = Calendar.getInstance(),
    var passed: Boolean = false,
    var title: String? = "",
    val desc: String? = "",
) : Parcelable {
    constructor(parcel: Parcel) : this(
        Calendar.getInstance().fromLong(parcel.readLong()),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(date.time.time)
        parcel.writeByte(if (passed) 1 else 0)
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
}

fun Calendar.fromLong(long: Long): Calendar {
    val cal = Calendar.getInstance()
    cal.time.time = long
    return cal
}