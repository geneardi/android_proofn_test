package android.proofn.test.interactors.outputs

import android.proofn.test.entities.MessageModel

interface GetMessageDetailListener {
    fun onResponse(messageModel: MessageModel)
    fun onFailure(throwable: Throwable)
}