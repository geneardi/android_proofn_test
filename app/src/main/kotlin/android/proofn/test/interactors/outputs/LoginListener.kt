package android.proofn.test.interactors.outputs

import android.proofn.test.entities.responses.LoginResponse

interface LoginListener {
    fun onResponse(loginResponse: LoginResponse)
    fun onFailure(throwable: Throwable)
}