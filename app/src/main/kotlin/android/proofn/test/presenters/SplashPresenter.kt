package android.proofn.test.presenters

import android.proofn.test.contracts.BaseContract
import android.proofn.test.contracts.SplashContract
import android.proofn.test.views.activities.FormLoginActivity
import android.proofn.test.views.activities.HomeActivity
import com.orhanobut.hawk.Hawk
import com.orhanobut.hawk.HawkBuilder
import com.orhanobut.hawk.LogLevel
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class SplashPresenter(
        private var splashView: SplashContract.View,
        splashInteractor: SplashContract.Interactor)
    : BasePresenter<BaseContract.View, BaseContract.Interactor>(splashView, splashInteractor),
        SplashContract.Presenter {

    @Inject
    lateinit var router: Router
    lateinit var token: String


    override fun onViewCreated() {
        super.onViewCreated()
        Hawk.init(splashView.getContext())
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSqliteStorage(splashView.getContext()))
                .setLogLevel(LogLevel.FULL)
                .build()
//        token = Hawk.get("token", "token")
        if(Hawk.contains("token")){
            token = Hawk.get("token")
            router.navigateTo(HomeActivity.TAG,token)

        }else{
            router.navigateTo(FormLoginActivity.TAG)
        }
        splashView.finishView()

}}