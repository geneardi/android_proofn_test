package android.proofn.test.interactors.outputs

import android.proofn.test.entities.AvatarModel

interface SendImageListener {
    fun onResponse(avatarModel: AvatarModel)
    fun onFailure(throwable: Throwable)
}