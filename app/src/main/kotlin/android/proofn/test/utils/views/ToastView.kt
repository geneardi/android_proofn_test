package android.proofn.test.utils.views

import android.annotation.SuppressLint
import android.content.Context
import android.proofn.test.R
import android.support.v4.content.ContextCompat
import android.widget.Toast
import es.dmoral.toasty.Toasty

class ToastView
constructor(private val context: Context){

    fun error (message: String){
        Toasty.error(context, message, Toast.LENGTH_SHORT, true).show()
    }

}