package android.proofn.test.interactors.outputs

import android.proofn.test.entities.responses.MessageResponse

interface GetMessageListener {
    fun onResponse(messageResponse: MessageResponse)
    fun onFailure(throwable: Throwable)
}