package com.app.telemed.viewModels

import android.os.Parcel
import android.os.Parcelable

data class Promocode(
    var id:Int = 0,
    var name: String? = "",
    var date: String? = "",
    var isPassed:Boolean = false,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(date)
        parcel.writeByte(if (isPassed) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Promocode> {
        override fun createFromParcel(parcel: Parcel): Promocode {
            return Promocode(parcel)
        }

        override fun newArray(size: Int): Array<Promocode?> {
            return arrayOfNulls(size)
        }
    }
}
