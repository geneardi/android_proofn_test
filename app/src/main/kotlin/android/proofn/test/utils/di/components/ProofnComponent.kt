package android.proofn.test.utils.di.components

import dagger.BindsInstance
import dagger.Component
import android.proofn.test.contracts.BaseContract
import android.proofn.test.presenters.*
import android.proofn.test.utils.di.modules.*
import javax.inject.Singleton

@Singleton
@Component(modules = [(ActivityModule::class), (NetworkModule::class), (PrefModule::class),
    (NavigationModule::class), (ViewModule::class)])
interface ProofnComponent {

    fun inject(formLoginPresenter: FormLoginPresenter)
    fun inject(homePresenter: HomePresenter)
    fun inject(messageDetailPresenter: MessageDetailPresenter)
    fun inject(splashPresenter: SplashPresenter)
    fun inject(sendMessagePresenter: SendMessagePresenter)
    fun inject(imageFullScreenPresenter: ImageFullScreenPresenter)
    fun inject(editProfilePresenter: EditProfilePresenter)

    @Component.Builder
    interface Builder {
        fun build(): ProofnComponent

        fun networkModule(networkModule: NetworkModule): Builder
        fun activityModule(activityModule : ActivityModule): Builder
        fun prefModule(prefModule: PrefModule) : Builder
        fun navigationModule(navigationModule: NavigationModule) : Builder
        fun viewModule(viewModule: ViewModule): Builder

        @BindsInstance
        fun baseView(baseView: BaseContract.View): Builder
        @BindsInstance
        fun baseInteractor(baseInteractor: BaseContract.Interactor) : Builder
    }
}