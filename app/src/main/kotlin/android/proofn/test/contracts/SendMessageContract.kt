package android.proofn.test.contracts

import android.proofn.test.interactors.outputs.GetMessageDetailListener
import android.proofn.test.interactors.outputs.SendMessageListener
import android.proofn.test.utils.networks.ProofnRequestService
import android.proofn.test.utils.views.ProgressDialogView

interface SendMessageContract {
    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter

    interface Interactor : BaseContract.Interactor{
        fun getMessage(proofnRequestService: ProofnRequestService, sendMessageListener:
        SendMessageListener, progressDialogView: ProgressDialogView,
                       token: String, subject:String,content:String)

    }
}