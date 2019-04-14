package android.proofn.test.entities.responses

import android.os.Parcel
import android.os.Parcelable
import android.proofn.test.entities.MessageModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DefaultResponse (@SerializedName("message") @Expose val message : String?) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DefaultResponse> {
        override fun createFromParcel(parcel: Parcel): DefaultResponse {
            return DefaultResponse(parcel)
        }

        override fun newArray(size: Int): Array<DefaultResponse?> {
            return arrayOfNulls(size)
        }
    }

}