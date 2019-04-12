package android.proofn.test.interactors.outputs

import android.proofn.test.entities.MessageSentModel

interface SendMessageListener {
    fun onResponse(sentModel: MessageSentModel)
    fun onFailure(throwable: Throwable)
}