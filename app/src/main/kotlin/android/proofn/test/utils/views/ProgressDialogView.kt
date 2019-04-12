package android.proofn.test.utils.views

import android.app.Activity
import android.app.Dialog
import android.proofn.test.R
import android.view.Window
import android.view.WindowManager

class ProgressDialogView(private val activity: Activity) {

    fun showProgressDialog(): Dialog {
        val progressDialog = Dialog(activity)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.setCancelable(false)

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(progressDialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        progressDialog.show()
        progressDialog.window!!.attributes = lp

        return progressDialog
    }

    fun closeProgressDialog(progressDialog: Dialog) {
        progressDialog.dismiss()
        progressDialog.cancel()
    }
}