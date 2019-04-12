package android.proofn.test.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SenderModel (@SerializedName("id") @Expose val id: String?,
                          @SerializedName("hash") @Expose val hash: String?,
                          @SerializedName("fullName") @Expose val fullName: String?,
                          @SerializedName("email") @Expose val email: String?,
                          @SerializedName("phoneNumber") @Expose val phoneNumber: String?,
                          @SerializedName("avatarPathSmall") @Expose val avatarPathSmall: String?,
                          @SerializedName("avatarPathMedium") @Expose val avatarPathMedium: String?,
                          @SerializedName("avatarPathLarge") @Expose val avatarPathLarge: String?
)
    : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(hash)
        parcel.writeString(fullName)
        parcel.writeString(email)
        parcel.writeString(phoneNumber)
        parcel.writeString(avatarPathSmall)
        parcel.writeString(avatarPathMedium)
        parcel.writeString(avatarPathLarge)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SenderModel> {
        override fun createFromParcel(parcel: Parcel): SenderModel {
            return SenderModel(parcel)
        }

        override fun newArray(size: Int): Array<SenderModel?> {
            return arrayOfNulls(size)
        }
    }
}