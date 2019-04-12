package android.proofn.test.entities.messages

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SendMessageMessage (@SerializedName("subject") @Expose val subject: String?,
                          @SerializedName("content") @Expose val content: String?)
    : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(subject)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SendMessageMessage> {
        override fun createFromParcel(parcel: Parcel): SendMessageMessage {
            return SendMessageMessage(parcel)
        }

        override fun newArray(size: Int): Array<SendMessageMessage?> {
            return arrayOfNulls(size)
        }
    }
}