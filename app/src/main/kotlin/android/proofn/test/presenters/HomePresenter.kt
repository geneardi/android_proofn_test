package android.proofn.test.presenters

import android.proofn.test.contracts.BaseContract
import android.proofn.test.contracts.HomeContract
import android.proofn.test.utils.networks.ProofnRequestService
import android.proofn.test.utils.views.ProgressDialogView
import android.proofn.test.views.activities.HomeActivity
import android.proofn.test.views.activities.ImageFullScreenActivity
import android.proofn.test.views.activities.MessageDetailActivity
import android.proofn.test.views.activities.SendMessageActivity
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class HomePresenter(homeView: HomeContract.View,
                    private var homeInteractor: HomeContract.Interactor)
    : BasePresenter<BaseContract.View, BaseContract.Interactor>(homeView, homeInteractor),
        HomeContract.Presenter {

    @Inject
    lateinit var progressDialogView: ProgressDialogView
    @Inject
    lateinit var proofnRequestService: ProofnRequestService
    @Inject
    lateinit var router: Router

    fun onSendMessageSelected() {
            router.navigateTo(SendMessageActivity.TAG)
    }

    fun onImageSelected(url: String?) {
        router.navigateTo(ImageFullScreenActivity.TAG, url)
    }

    fun onItemSelected(id: String) {
        router.navigateTo(MessageDetailActivity.TAG, id)
    }

    fun getMessage(getMessageEventListener: HomeActivity.GetMessageEventListener, token: String){
        homeInteractor.getMessage(proofnRequestService, getMessageEventListener, progressDialogView, token)
    }

    fun getUserProfile(getUserProfileEventListener: HomeActivity.GetUserProfileEventListener, token: String){
        homeInteractor.getUserProfile(proofnRequestService, getUserProfileEventListener, progressDialogView, token)
    }
}