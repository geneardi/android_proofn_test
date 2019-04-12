package android.proofn.test.utils.prefs

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.proofn.test.R

@SuppressLint("CommitPrefEdits")
class IntroPref
constructor(activity: Activity) {

    private val prefName: String by lazy { activity.getString(R.string.pref_name) }
    private val prefIntroStatus: String by lazy { activity.getString(R.string.pref_intro_status) }
    private val pref: SharedPreferences by lazy { activity.getSharedPreferences(prefName, MODE_PRIVATE) }
    private val editor: SharedPreferences.Editor by lazy { pref.edit() }

    var introStatus: Boolean
        get() = pref.getBoolean(prefIntroStatus, false)
        set(introStatus) {
            editor.putBoolean(prefIntroStatus, introStatus)
            editor.apply()
        }

    fun clear() {
        editor.remove(prefIntroStatus)
        editor.commit()
    }
}