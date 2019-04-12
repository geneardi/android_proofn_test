package android.proofn.test.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MessageModel (@SerializedName("id") @Expose val id: String?,
                         @SerializedName("contentType") @Expose val contentType: String?,
                         @SerializedName("box") @Expose val box: String?,
                         @SerializedName("subject") @Expose val subject: String?,
                         @SerializedName("subjectPreview") @Expose val subjectPreview: String?,
                         @SerializedName("content") @Expose val content: String?,
                         @SerializedName("contentPreview") @Expose val contentPreview: String?,
                         @SerializedName("deliveryStatus") @Expose val deliveryStatus: String?,
                         @SerializedName("createdAt") @Expose val createdAt: String?,
                         @SerializedName("sentAt") @Expose val sentAt: String?,
                         @SerializedName("sender") @Expose val sender: SenderModel?,
                         @SerializedName("receivedAt") @Expose val receivedAt: String?,
                         @SerializedName("isRead") @Expose val isRead: String?
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
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(SenderModel::class.java.classLoader),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(contentType)
        parcel.writeString(box)
        parcel.writeString(subject)
        parcel.writeString(subjectPreview)
        parcel.writeString(content)
        parcel.writeString(contentPreview)
        parcel.writeString(deliveryStatus)
        parcel.writeString(createdAt)
        parcel.writeString(sentAt)
        parcel.writeParcelable(sender, flags)
        parcel.writeString(receivedAt)
        parcel.writeString(isRead)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MessageSentModel> {
        override fun createFromParcel(parcel: Parcel): MessageSentModel {
            return MessageSentModel(parcel)
        }

        override fun newArray(size: Int): Array<MessageSentModel?> {
            return arrayOfNulls(size)
        }
    }


}