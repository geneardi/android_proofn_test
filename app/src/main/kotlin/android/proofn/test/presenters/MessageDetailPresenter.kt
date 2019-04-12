package android.proofn.test.presenters

import android.proofn.test.contracts.BaseContract
import android.proofn.test.contracts.MessageDetailContract
import android.proofn.test.utils.networks.ProofnRequestService
import android.proofn.test.utils.views.ProgressDialogView
import android.proofn.test.views.activities.MessageDetailActivity
import javax.inject.Inject

class MessageDetailPresenter (messageDetailView: MessageDetailContract.View,
                              private var messageDetailInteractor: MessageDetailContract.Interactor)
    : BasePresenter<BaseContract.View, BaseContract.Interactor>(messageDetailView, messageDetailInteractor),
        MessageDetailContract.Presenter{
    @Inject
    lateinit var progressDialogView: ProgressDialogView
    @Inject
    lateinit var proofnRequestService: ProofnRequestService

    fun getMessageDetail(getMessageDetailEventListener: MessageDetailActivity.GetMessageDetailEventListener, token: String, id: String){
        messageDetailInteractor.getMessage(proofnRequestService, getMessageDetailEventListener, progressDialogView, token, id)
    }
}