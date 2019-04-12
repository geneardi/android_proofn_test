package android.proofn.test.interactors

import android.app.Dialog
import android.util.Log
import android.proofn.test.contracts.FormLoginContract
import android.proofn.test.entities.messages.LoginMessage
import android.proofn.test.interactors.outputs.LoginListener
import android.proofn.test.utils.networks.ProofnRequestService
import android.proofn.test.utils.views.ProgressDialogView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FormLoginInteractor : FormLoginContract.Interactor{

    private var subscriptionGenerateToken: Disposable? = null

    override fun login(proofnRequestService: ProofnRequestService,
                       loginListener: LoginListener,
                       progressDialogView: ProgressDialogView,
                       identifier: String, password: String) {
        Log.i("login-masuk", "masuk")
        val dialog: Dialog = progressDialogView.showProgressDialog()
        Log.i("login-masuk", "loading")
        subscriptionGenerateToken = proofnRequestService
                .login(LoginMessage(identifier, password))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {loginResponse ->
                            run {
                                Log.i("login-masuk", "baseResponse")
                                Log.i("login-data", loginResponse.token)
                                if (!loginResponse.token.isNullOrEmpty())
                                    progressDialogView.closeProgressDialog(dialog)
                                    loginListener.onResponse(loginResponse)
                            }
                        },
                        {error ->
                            run {
                                if (error.message.isNullOrEmpty()) {
                                    Log.i("login-masuk", "error")
                                    progressDialogView.closeProgressDialog(dialog)
                                    loginListener.onFailure(error)

                                }
                            }
                        }
                )
    }
}