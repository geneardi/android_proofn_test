package android.proofn.test.utils.prefs

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.proofn.test.R
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

@SuppressLint("CommitPrefEdits")
class TokenPref
constructor(activity: Activity) {

    private val prefName: String by lazy { activity.getString(R.string.pref_name) }
    private val prefToken: String by lazy { activity.getString(R.string.pref_token) }
    private val prefTokenExp: String by lazy { activity.getString(R.string.pref_token_exp) }
    private val datetimeFormat: String by lazy { activity.getString(R.string.datetime_format) }
    private val pref: SharedPreferences by lazy { activity.getSharedPreferences(prefName, MODE_PRIVATE) }
    private val editor: SharedPreferences.Editor by lazy { pref.edit() }

    var token: String?
        get() = pref.getString(prefToken, null)
        set(token) {
            editor.putString(prefToken, token)
            editor.apply()
        }

    var tokenExp: DateTime?
        get() {
            val tokenExpVal = pref.getString(prefTokenExp, null)
            return if (tokenExpVal.isNullOrEmpty())
                null
            else {
                val formatter = DateTimeFormat
                        .forPattern(datetimeFormat)
                formatter.parseDateTime(tokenExpVal)
            }
        }
        set(tokenExp) {
            editor.putString(prefTokenExp, tokenExp.toString())
            editor.apply()
        }

    fun clear() {
        editor.remove(prefToken)
        editor.remove(prefTokenExp)
        editor.commit()
    }
}