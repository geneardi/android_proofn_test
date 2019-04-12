package android.proofn.test.utils.views

import android.app.Activity
import android.os.Build
import android.view.Window
import android.view.WindowManager

class StatusBarView
constructor(private val activity: Activity){

    private val window: Window by lazy { activity.window }

    fun changeColor(color : Int){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }
    }
}