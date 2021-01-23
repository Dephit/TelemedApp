package com.app.telemed.viewModels

import android.os.Parcel
import android.os.Parcelable

data class Comment(
    var id: Int = 0,
    var isSeen: Boolean? = false,
    var groupText: String? = "",
    var lessonText: String? = "",
    var commentText: String? = "",
    var dataText: String? = "",
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeByte(if (isSeen == true) 1 else 0)
        parcel.writeString(groupText)
        parcel.writeString(lessonText)
        parcel.writeString(commentText)
        parcel.writeString(dataText)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Comment> {
        override fun createFromParcel(parcel: Parcel): Comment {
            return Comment(parcel)
        }

        override fun newArray(size: Int): Array<Comment?> {
            return arrayOfNulls(size)
        }
    }
}
