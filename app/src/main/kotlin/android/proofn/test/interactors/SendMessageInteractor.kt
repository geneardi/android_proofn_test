package android.proofn.test.interactors

import android.app.Dialog
import android.proofn.test.contracts.SendMessageContract
import android.proofn.test.entities.messages.SendMessageMessage
import android.proofn.test.interactors.outputs.SendMessageListener
import android.proofn.test.utils.networks.ProofnRequestService
import android.proofn.test.utils.views.ProgressDialogView
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SendMessageInteractor : SendMessageContract.Interactor{
    private var subscription: Disposable? = null
    override fun getMessage(proofnRequestService: ProofnRequestService, sendMessageListener:
    SendMessageListener, progressDialogView: ProgressDialogView, token: String, subject:String,
                            content:String) {
        Log.i("pesan-masuk", "masuk")
        val dialog: Dialog = progressDialogView.showProgressDialog()
        Log.i("pesan-masuk", "loading")
        subscription = proofnRequestService
                .sendMessage(token,(SendMessageMessage(subject,content)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate {
                    progressDialogView.closeProgressDialog(dialog) }
                .subscribe(
                        {response ->
                            run {
                                Log.i("pesan-masuk", "baseResponse")
                                if (!response.id.isNullOrEmpty()){
                                    sendMessageListener.onResponse(response)
                                }
                            }
                        },
                        {error ->
                            run {
                                if (!error.message.isNullOrEmpty()){
                                    Log.i("pesan-error", "error")
                                    sendMessageListener.onFailure(error)
                                }
                            }
                        }
                )
    }
}