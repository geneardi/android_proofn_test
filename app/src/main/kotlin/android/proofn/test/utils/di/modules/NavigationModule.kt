package android.proofn.test.utils.di.modules

import dagger.Module
import dagger.Provides
import dagger.Reusable
import android.proofn.test.BaseApplication
import ru.terrakok.cicerone.Router

@Module
object NavigationModule{
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRouter() : Router {
        return BaseApplication.INSTANCE.proofnCicerone.router
    }
}