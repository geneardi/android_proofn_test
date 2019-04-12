package android.proofn.test.presenters

import android.proofn.test.contracts.BaseContract
import android.proofn.test.contracts.ImageFullScreenContract
import android.proofn.test.utils.networks.ProofnRequestService
import android.proofn.test.utils.views.ProgressDialogView
import android.proofn.test.views.activities.ImageFullScreenActivity
import android.proofn.test.views.activities.MessageDetailActivity
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class ImageFullScreenPresenter(homeView: ImageFullScreenContract.View,
                               private var homeInteractor: ImageFullScreenContract.Interactor)
    : BasePresenter<BaseContract.View, BaseContract.Interactor>(homeView, homeInteractor),
        ImageFullScreenContract.Presenter {

    @Inject
    lateinit var progressDialogView: ProgressDialogView
    @Inject
    lateinit var proofnRequestService: ProofnRequestService
    @Inject
    lateinit var router: Router

    fun onItemSelected() {
        router.navigateTo(MessageDetailActivity.TAG)
    }

    fun getMessage(getMessageEventListener: ImageFullScreenActivity.GetMessageEventListener, token: String){
        homeInteractor.getMessage(proofnRequestService, getMessageEventListener, progressDialogView, token)
    }

}