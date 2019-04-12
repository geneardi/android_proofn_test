package android.proofn.test.entities.messages

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginMessage (@SerializedName("identifier") @Expose val identifier: String?,
                    @SerializedName("password") @Expose val password: String?)
    : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(identifier)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginMessage> {
        override fun createFromParcel(parcel: Parcel): LoginMessage {
            return LoginMessage(parcel)
        }

        override fun newArray(size: Int): Array<LoginMessage?> {
            return arrayOfNulls(size)
        }
    }
}