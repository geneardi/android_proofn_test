package android.proofn.test.entities.responses

import android.os.Parcel
import android.os.Parcelable
import android.proofn.test.entities.MessageModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MessageResponse (@SerializedName("data") @Expose val categorydata : List<MessageModel>) : Parcelable {
    constructor(parcel: Parcel) : this(arrayListOf<MessageModel>().apply {
        parcel.readArrayList(MessageModel::class.java.classLoader)
    })

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MessageResponse> {
        override fun createFromParcel(parcel: Parcel): MessageResponse {
            return MessageResponse(parcel)
        }

        override fun newArray(size: Int): Array<MessageResponse?> {
            return arrayOfNulls(size)
        }
    }
}