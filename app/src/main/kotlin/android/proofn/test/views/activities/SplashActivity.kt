package android.proofn.test.views.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.proofn.test.BaseApplication
import android.proofn.test.R
import android.proofn.test.contracts.SplashContract
import android.proofn.test.interactors.SplashInteractor
import android.proofn.test.presenters.SplashPresenter
import android.util.Log
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

class SplashActivity : BaseActivity<SplashPresenter>(), SplashContract.View {

    private val navigator: Navigator? by lazy {
        object : Navigator {
            override fun applyCommand(command: Command) {
                if (command is Forward) {
                    forward(command)
                }
            }

            private fun forward(command: Forward) {
                val data = (command.transitionData)
                when (command.screenKey) {
                    HomeActivity.TAG -> startActivity(Intent(this@SplashActivity, HomeActivity::class.java).putExtra("token", data as String))
                    FormLoginActivity.TAG -> startActivity(Intent(this@SplashActivity, FormLoginActivity::class.java))
                    else -> Log.e("Cicerone", "Unknown screen: " + command.screenKey)
                }
            }
        }
    }

    override fun instantiatePresenter(): SplashPresenter {
        return SplashPresenter(this, SplashInteractor())
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
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

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
