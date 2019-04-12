package android.proofn.test.utils.di.modules

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.Reusable
import android.proofn.test.utils.views.ProgressDialogView
import android.proofn.test.utils.views.StatusBarView
import android.proofn.test.utils.views.ToastView

@Module
object ViewModule {
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideStatusBarView(activity: Activity): StatusBarView {
        return StatusBarView(activity)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideProgressDialogView(activity: Activity): ProgressDialogView {
        return ProgressDialogView(activity)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideToastView(context: Context): ToastView {
        return ToastView(context)
    }
}