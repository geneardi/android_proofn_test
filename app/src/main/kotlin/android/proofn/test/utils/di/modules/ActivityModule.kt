package android.proofn.test.utils.di.modules

import android.app.Activity
import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import android.proofn.test.BaseApplication
import android.proofn.test.contracts.BaseContract

@Module
object ActivityModule{
    @Provides
    @JvmStatic
    internal fun provideActivity(baseView: BaseContract.View): Activity {
        return baseView.getActivity()
    }

    @Provides
    @JvmStatic
    internal fun provideContext(baseView: BaseContract.View): Context {
        return baseView.getContext()
    }

    @Provides
    @JvmStatic
    internal fun provideApplication(context: Context): Application {
        return context.applicationContext as BaseApplication
    }
}