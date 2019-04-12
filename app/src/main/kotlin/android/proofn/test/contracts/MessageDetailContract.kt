package android.proofn.test.contracts

import android.proofn.test.interactors.outputs.GetMessageDetailListener
import android.proofn.test.utils.networks.ProofnRequestService
import android.proofn.test.utils.views.ProgressDialogView

interface MessageDetailContract {
    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter

    interface Interactor : BaseContract.Interactor{
        fun getMessage(proofnRequestService: ProofnRequestService,
                       getMessageDetailListener: GetMessageDetailListener,
                       progressDialogView: ProgressDialogView, token: String, id: String)

    }
}