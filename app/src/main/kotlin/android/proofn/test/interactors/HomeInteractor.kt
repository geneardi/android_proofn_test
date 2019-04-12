package android.proofn.test.interactors

import android.app.Dialog
import android.proofn.test.contracts.HomeContract
import android.proofn.test.interactors.outputs.GetMessageListener
import android.proofn.test.interactors.outputs.GetUserProfileListener
import android.proofn.test.utils.networks.ProofnRequestService
import android.proofn.test.utils.views.ProgressDialogView
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeInteractor : HomeContract.Interactor {
    private var subscription: Disposable? = null

    override fun getUserProfile(proofnRequestService: ProofnRequestService, getUserProfileListener: GetUserProfileListener, progressDialogView: ProgressDialogView, token: String) {
        Log.i("profile-masuk", "masuk")
        val dialog: Dialog = progressDialogView.showProgressDialog()
        Log.i("profile-masuk", "loading")
        subscription = proofnRequestService
                .getUserProfile(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate {
                    progressDialogView.closeProgressDialog(dialog) }
                .subscribe(
                        {response ->
                            run {
                                Log.i("profile-masuk", "baseResponse")
                                if (!response.id.isNullOrEmpty()){
                                    progressDialogView.closeProgressDialog(dialog)
                                    getUserProfileListener.onResponse(response)
                                }
                            }
                        },
                        {error ->
                            run {
                                if (!error.message.isNullOrEmpty()){
                                    Log.i("profile-error", "error")
                                    progressDialogView.closeProgressDialog(dialog)
                                    getUserProfileListener.onFailure(error)
                                }
                            }
                        }
                )
    }

    override fun getMessage(proofnRequestService: ProofnRequestService,
                            getMessageListener: GetMessageListener,
                            progressDialogView: ProgressDialogView, token: String) {
        Log.i("pesan-masuk", "masuk")
        val dialog: Dialog = progressDialogView.showProgressDialog()
        Log.i("pesan-masuk", "loading")
        subscription = proofnRequestService
                .getMessage(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate {
                    progressDialogView.closeProgressDialog(dialog) }
                .subscribe(
                        {response ->
                            run {
                                Log.i("pesan-masuk", "baseResponse")
                                if (!response.categorydata.isNullOrEmpty()){
                                    getMessageListener.onResponse(response)
                                }
                            }
                        },
                        {error ->
                            run {
                                if (!error.message.isNullOrEmpty()){
                                    Log.i("pesan-error", "error")
                                    getMessageListener.onFailure(error)
                                }
                            }
                        }
                )
    }
}