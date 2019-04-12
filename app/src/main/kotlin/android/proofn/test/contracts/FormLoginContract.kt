package android.proofn.test.contracts

import android.proofn.test.interactors.outputs.LoginListener
import android.proofn.test.utils.networks.ProofnRequestService
import android.proofn.test.utils.views.ProgressDialogView

interface FormLoginContract {
    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter

    interface Interactor : BaseContract.Interactor{
        fun login(proofnRequestService: ProofnRequestService,
                  loginListener: LoginListener,
                  progressDialogView: ProgressDialogView, identifier: String, password: String)
    }
}