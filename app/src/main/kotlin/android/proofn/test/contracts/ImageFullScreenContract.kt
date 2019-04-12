package android.proofn.test.contracts

import android.proofn.test.interactors.outputs.GetMessageListener
import android.proofn.test.interactors.outputs.GetUserProfileListener
import android.proofn.test.utils.networks.ProofnRequestService
import android.proofn.test.utils.views.ProgressDialogView

interface ImageFullScreenContract {
    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter

    interface Interactor : BaseContract.Interactor{
        fun getMessage(proofnRequestService: ProofnRequestService,
                       getMessageListener: GetMessageListener,
                       progressDialogView: ProgressDialogView, token: String)

        fun getUserProfile(proofnRequestService: ProofnRequestService,
                           getUserProfileListener: GetUserProfileListener,
                           progressDialogView: ProgressDialogView, token: String)
    }
}