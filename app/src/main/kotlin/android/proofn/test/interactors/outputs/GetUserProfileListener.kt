package android.proofn.test.interactors.outputs

import android.proofn.test.entities.UserModel

interface GetUserProfileListener {
    fun onResponse(userModel: UserModel)
    fun onFailure(throwable: Throwable)
}