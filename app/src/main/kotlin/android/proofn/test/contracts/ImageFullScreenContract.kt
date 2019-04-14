package android.proofn.test.contracts

import android.proofn.test.interactors.outputs.SendImageListener
import android.proofn.test.utils.networks.ProofnRequestService
import android.proofn.test.utils.views.ProgressDialogView
import okhttp3.MultipartBody

interface ImageFullScreenContract {
    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter

    interface Interactor : BaseContract.Interactor{
        fun sendImage(proofnRequestService: ProofnRequestService,
                       sendImageListener: SendImageListener,
                       progressDialogView: ProgressDialogView, token: String, image: MultipartBody.Part)


    }
}