package android.proofn.test.presenters

import android.content.Context
import android.util.Log
import android.widget.Toast
import android.proofn.test.contracts.BaseContract
import android.proofn.test.contracts.FormLoginContract
import android.proofn.test.entities.responses.LoginResponse
import android.proofn.test.interactors.outputs.LoginListener
import android.proofn.test.utils.networks.ProofnRequestService
import android.proofn.test.utils.views.ProgressDialogView
import android.proofn.test.views.activities.HomeActivity
import com.orhanobut.hawk.Hawk
import com.orhanobut.hawk.HawkBuilder
import com.orhanobut.hawk.LogLevel
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class FormLoginPresenter(
        private var formLoginView: FormLoginContract.View,
        private var formLoginInteractor: FormLoginContract.Interactor)
    : BasePresenter<BaseContract.View, BaseContract.Interactor>(formLoginView, formLoginInteractor),
        FormLoginContract.Presenter {

    lateinit var context: Context
    @Inject
    lateinit var router: Router
    @Inject
    lateinit var progressDialogView: ProgressDialogView
    @Inject
    lateinit var proofnRequestService: ProofnRequestService

    private inner class LoginEvenListener : LoginListener {
        override fun onResponse(loginResponse: LoginResponse) {
//            Toast.makeText(formLoginView.getActivity(), "login berhasil",
//                    Toast.LENGTH_LONG).show()

//            Hawk.init(context)
//                    .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
//                    .setStorage(HawkBuilder.newSqliteStorage(context))
//                    .setLogLevel(LogLevel.FULL)
//                    .build()
//            val data = loginResponse.token
//            Log.i("login-token", loginResponse.token)
            val token = loginResponse.token
//            Hawk.put("token", data)
            router.navigateTo(HomeActivity.TAG, token)

            view.finishView()
        }

        override fun onFailure(throwable: Throwable) {
            if(throwable.message != null) {
                Log.i("login-failed", throwable.message)
                Toast.makeText(formLoginView.getActivity(), "login gagal",
                        Toast.LENGTH_LONG).show()
            }
        }
    }

    fun login(identifier: String, password :String){
        formLoginInteractor.login(proofnRequestService, LoginEvenListener(), progressDialogView, identifier, password)
    }
}