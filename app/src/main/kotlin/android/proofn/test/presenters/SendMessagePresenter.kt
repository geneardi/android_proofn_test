package android.proofn.test.presenters

import android.proofn.test.contracts.BaseContract
import android.proofn.test.contracts.HomeContract
import android.proofn.test.contracts.SendMessageContract
import android.proofn.test.utils.networks.ProofnRequestService
import android.proofn.test.utils.views.ProgressDialogView
import android.proofn.test.views.activities.SendMessageActivity
import javax.inject.Inject

class SendMessagePresenter (messageDetailView: SendMessageContract.View,
                            private var messageDetailInteractor: SendMessageContract.Interactor)
    : BasePresenter<BaseContract.View, BaseContract.Interactor>(messageDetailView, messageDetailInteractor),
        HomeContract.Presenter{
    @Inject
    lateinit var progressDialogView: ProgressDialogView
    @Inject
    lateinit var proofnRequestService: ProofnRequestService

    fun getMessageDetail(getMessageDetailEventListener: SendMessageActivity.GetMessageDetailEventListener, token: String, subject: String, content: String){
        messageDetailInteractor.getMessage(proofnRequestService, getMessageDetailEventListener, progressDialogView, token, subject, content)
    }
}