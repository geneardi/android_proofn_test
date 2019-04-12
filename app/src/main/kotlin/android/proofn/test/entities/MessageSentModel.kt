package android.proofn.test.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MessageSentModel (@SerializedName("id") @Expose val id: String?,
                             @SerializedName("contentType") @Expose val contentType: String?,
                             @SerializedName("hash") @Expose val hash: String?,
                             @SerializedName("messageClass") @Expose val messageClass: Int?,
                             @SerializedName("messageType") @Expose val messageType: String?,
                             @SerializedName("content") @Expose val content: String?,
                             @SerializedName("recipientEmail") @Expose val recipientEmail: String?,
                             @SerializedName("deliveryStatus") @Expose val deliveryStatus: String?,
                             @SerializedName("createdAt") @Expose val createdAt: String?,
                             @SerializedName("sentAt") @Expose val sentAt: String?,
                             @SerializedName("sender") @Expose val sender: SenderModel?,
                             @SerializedName("receivedAt") @Expose val receivedAt: String?,
                             @SerializedName("isRead") @Expose val isRead: String?,
                             @SerializedName("senderEmail") @Expose val senderEmail: String?
)
    : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(SenderModel::class.java.classLoader),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(contentType)
        parcel.writeString(hash)
        parcel.writeValue(messageClass)
        parcel.writeString(messageType)
        parcel.writeString(content)
        parcel.writeString(recipientEmail)
        parcel.writeString(deliveryStatus)
        parcel.writeString(createdAt)
        parcel.writeString(sentAt)
        parcel.writeParcelable(sender, flags)
        parcel.writeString(receivedAt)
        parcel.writeString(isRead)
        parcel.writeString(senderEmail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MessageModel> {
        override fun createFromParcel(parcel: Parcel): MessageModel {
            return MessageModel(parcel)
        }

        override fun newArray(size: Int): Array<MessageModel?> {
            return arrayOfNulls(size)
        }
    }


}