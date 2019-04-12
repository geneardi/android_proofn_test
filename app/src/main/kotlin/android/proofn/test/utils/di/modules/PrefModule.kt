package android.proofn.test.utils.di.modules

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.Reusable
import android.proofn.test.utils.prefs.IntroPref
import android.proofn.test.utils.prefs.TokenPref
import android.proofn.test.utils.prefs.UserPref

@Module
object PrefModule {
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideIntroPref(activity: Activity): IntroPref {
        return IntroPref(activity)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideTokenPref(activity: Activity): TokenPref {
        return TokenPref(activity)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideUserPref(activity: Activity): UserPref {
        return UserPref(activity)
    }
}