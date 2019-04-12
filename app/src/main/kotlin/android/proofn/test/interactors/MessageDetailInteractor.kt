package android.proofn.test.interactors

import android.app.Dialog
import android.proofn.test.contracts.MessageDetailContract
import android.proofn.test.interactors.outputs.GetMessageDetailListener
import android.proofn.test.utils.networks.ProofnRequestService
import android.proofn.test.utils.views.ProgressDialogView
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MessageDetailInteractor : MessageDetailContract.Interactor{
    private var subscription: Disposable? = null
    override fun getMessage(proofnRequestService: ProofnRequestService, getMessageDetailListener: GetMessageDetailListener, progressDialogView: ProgressDialogView, token: String, id: String) {
        Log.i("pesan-masuk", "masuk")
        val dialog: Dialog = progressDialogView.showProgressDialog()
        Log.i("pesan-masuk", "loading")
        subscription = proofnRequestService
                .getMessageDetail(token, id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate {
                    progressDialogView.closeProgressDialog(dialog) }
                .subscribe(
                        {response ->
                            run {
                                Log.i("pesan-masuk", "baseResponse")
                                if (!response.id.isNullOrEmpty()){
                                    getMessageDetailListener.onResponse(response)
                                }
                            }
                        },
                        {error ->
                            run {
                                if (!error.message.isNullOrEmpty()){
                                    Log.i("pesan-error", "error")
                                    getMessageDetailListener.onFailure(error)
                                }
                            }
                        }
                )
    }
}