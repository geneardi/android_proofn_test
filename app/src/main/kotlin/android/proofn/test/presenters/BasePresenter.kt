package android.proofn.test.presenters

import android.proofn.test.contracts.BaseContract
import android.proofn.test.utils.di.components.DaggerProofnComponent
import android.proofn.test.utils.di.components.ProofnComponent
import android.proofn.test.utils.di.modules.*

/**
 * Base presenter any presenter of the application must extend. It provides initial injections and
 * required methods.
 * @property view the view the presenter is based on
 * @property injector The injector used to inject required dependencies
 * @constructor Injects the required dependencies
 */
abstract class BasePresenter<out V : BaseContract.View, out I : BaseContract.Interactor>(
        protected val view: V, protected val interactor: I) : BaseContract.Presenter {
    private val injector: ProofnComponent by lazy {
        DaggerProofnComponent
            .builder()
            .baseView(view)
            .baseInteractor(interactor)
            .activityModule(ActivityModule)
            .networkModule(NetworkModule)
            .navigationModule(NavigationModule)
            .viewModule(ViewModule)
            .prefModule(PrefModule)
            .build()
    }

    init {
        inject()
    }

    /**
     * This method may be called when the presenter view is created
     */
    override fun onViewCreated(){}

    /**
     * This method may be called when the presenter view is destroyed
     */
    override fun onViewDestroyed(){}

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is FormLoginPresenter -> injector.inject(this)
            is HomePresenter -> injector.inject(this)
            is MessageDetailPresenter -> injector.inject(this)
            is SplashPresenter -> injector.inject(this)
            is SendMessagePresenter -> injector.inject(this)
            is ImageFullScreenPresenter -> injector.inject(this)
        }
    }
}