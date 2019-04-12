package android.proofn.test.views.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.proofn.test.BaseApplication
import android.proofn.test.R
import android.proofn.test.contracts.FormLoginContract
import android.proofn.test.interactors.FormLoginInteractor
import android.proofn.test.presenters.FormLoginPresenter
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.orhanobut.hawk.Hawk
import com.orhanobut.hawk.HawkBuilder
import com.orhanobut.hawk.LogLevel
import kotlinx.android.synthetic.main.activity_form_login.*
import okhttp3.Route
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import javax.inject.Inject

class FormLoginActivity : BaseActivity<FormLoginPresenter>(), FormLoginContract.View {

    companion object {
        const val TAG: String = "FormLoginActivity"
    }

    private val editTextEmaail: EditText? by lazy { edit_text_email }
    private val editTextPassword: EditText? by lazy { edit_text_password }
    private val buttonLogin: Button ? by lazy { button_login }
    var token: String? = null

    private val navigator: Navigator? by lazy {
        object : Navigator {
            override fun applyCommand(command: Command) {
                if (command is Forward) {
                    forward(command)
                }
            }

            private fun forward(command: Forward) {
                when (command.screenKey) {
//                    VerifyLoginActivity.TAG -> {
//                        val data = (command.transitionData as OtpModel)
//                        startActivity(Intent(this@FormLoginActivity, VerifyLoginActivity::class.java)
//                            .putExtra("data", data as Parcelable))
//                    }  // 4
                    HomeActivity.TAG -> startActivity(Intent(this@FormLoginActivity, HomeActivity::class.java))

                    else -> Log.e("Cicerone", "Unknown screen: " + command.screenKey)
                }
            }
        }
    }

    override fun instantiatePresenter(): FormLoginPresenter {
        return FormLoginPresenter(this, FormLoginInteractor())
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun getContext(): Context {
        return this
    }

    override fun finishView() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT <= 19) {
            setTheme(android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen)
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_login)
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSqliteStorage(this))
                .setLogLevel(LogLevel.FULL)
                .build()
        this.init()
    }

    private fun init(){

        buttonLogin?.setOnClickListener {
                presenter.login(editTextEmaail!!.text.toString(), editTextPassword!!.text.toString())
        }

    }

    override fun onResume() {
        super.onResume()
        BaseApplication.INSTANCE.proofnCicerone.navigatorHolder.setNavigator(navigator)
        presenter.onViewCreated()
    }

    override fun onPause() {
        super.onPause()
        BaseApplication.INSTANCE.proofnCicerone.navigatorHolder.removeNavigator()
    }


}