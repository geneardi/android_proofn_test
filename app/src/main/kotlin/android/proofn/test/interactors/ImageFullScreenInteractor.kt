package android.proofn.test.interactors

import android.app.Dialog
import android.proofn.test.contracts.ImageFullScreenContract
import android.proofn.test.interactors.outputs.SendImageListener
import android.proofn.test.utils.networks.ProofnRequestService
import android.proofn.test.utils.views.ProgressDialogView
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

class ImageFullScreenInteractor : ImageFullScreenContract.Interactor {
    private var subscription: Disposable? = null

    override fun sendImage(proofnRequestService: ProofnRequestService,
                            sendImageListener: SendImageListener,
                            progressDialogView: ProgressDialogView, token: String, image: MultipartBody.Part) {
        Log.i("pesan-masuk", "masuk")
        val dialog: Dialog = progressDialogView.showProgressDialog()
        Log.i("pesan-masuk", "loading")
        subscription = proofnRequestService
                .sendImage(token,image)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate {
                    progressDialogView.closeProgressDialog(dialog) }
                .subscribe(
                        {response ->
                            run {
                                Log.i("pesan-masuk", "baseResponse")
                                    sendImageListener.onResponse(response)
                                    progressDialogView.closeProgressDialog(dialog)

                            }
                        },
                        {error ->
                            run {
                                Log.i("pesan-error", "error")
                                sendImageListener.onFailure(error)
                                progressDialogView.closeProgressDialog(dialog)

                            }
                        }
                )
    }
}