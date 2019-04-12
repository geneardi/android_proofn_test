package android.proofn.test.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AvatarModel (@SerializedName("avatarPathSmall") @Expose val avatarPathSmall: String?,
                        @SerializedName("avatarPathMedium") @Expose val avatarPathMedium: String?,
                        @SerializedName("avatarPathLarge") @Expose val avatarPathLarge: String? ): Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(avatarPathSmall)
        parcel.writeString(avatarPathMedium)
        parcel.writeString(avatarPathLarge)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AvatarModel> {
        override fun createFromParcel(parcel: Parcel): AvatarModel {
            return AvatarModel(parcel)
        }

        override fun newArray(size: Int): Array<AvatarModel?> {
            return arrayOfNulls(size)
        }
    }

}