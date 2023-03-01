package com.melbourne.parking.model

import android.os.Parcel
import android.os.Parcelable

data class ParkingMeter(
    val id: String?,
    val streetName: String?,
    val lat: Double,
    val lng: Double,
    val hasCreditCard: Boolean,
    val hasTapAndGo: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(streetName)
        parcel.writeDouble(lat)
        parcel.writeDouble(lng)
        parcel.writeByte(if (hasCreditCard) 1 else 0)
        parcel.writeByte(if (hasTapAndGo) 1 else 0)
    }

    companion object CREATOR : Parcelable.Creator<ParkingMeter> {
        override fun createFromParcel(parcel: Parcel): ParkingMeter {
            return ParkingMeter(parcel)
        }

        override fun newArray(size: Int): Array<ParkingMeter?> {
            return arrayOfNulls(size)
        }
    }
}