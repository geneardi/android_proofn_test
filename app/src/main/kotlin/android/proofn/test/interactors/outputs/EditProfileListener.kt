package android.proofn.test.interactors.outputs

import android.proofn.test.entities.MessageSentModel
import android.proofn.test.entities.UserModel

interface EditProfileListener {
    fun onResponse(userModel: UserModel)
    fun onFailure(throwable: Throwable)
}