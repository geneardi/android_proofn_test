package android.proofn.test.utils.prefs

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.proofn.test.R

@SuppressLint("CommitPrefEdits")
class UserPref
constructor(activity: Activity) {

    private val prefName: String by lazy { activity.getString(R.string.pref_name) }
    private val prefUserId: String by lazy { activity.getString(R.string.pref_user_id) }
    private val prefUserPhone: String by lazy { activity.getString(R.string.pref_user_phone) }
    private val prefUserName: String by lazy { activity.getString(R.string.pref_user_name) }
    private val prefUserEmail: String by lazy { activity.getString(R.string.pref_user_email) }
    private val prefUserBalance: String by lazy { activity.getString(R.string.pref_user_balance) }
    private val pref: SharedPreferences by lazy { activity.getSharedPreferences(prefName, MODE_PRIVATE) }
    private val editor: SharedPreferences.Editor by lazy { pref.edit() }

    var userId: String?
        get() = pref.getString(prefUserId, null)
        set(userId) {
            editor.putString(prefUserId, userId)
            editor.apply()
        }

    var userPhone: String?
        get() = pref.getString(prefUserPhone, null)
        set(userPhone) {
            editor.putString(prefUserPhone, userPhone)
            editor.apply()
        }

    var userName: String?
        get() = pref.getString(prefUserName, null)
        set(userName) {
            editor.putString(prefUserName, userName)
            editor.apply()
        }

    var userEmail: String?
        get() = pref.getString(prefUserEmail, null)
        set(userEmail) {
            editor.putString(prefUserEmail, userEmail)
            editor.apply()
        }

    var userBalance: String?
        get() = pref.getString(prefUserBalance, null)
        set(userBalance) {
            editor.putString(prefUserBalance, userBalance)
            editor.apply()
        }

    fun clear() {
        editor.remove(prefUserId)
        editor.remove(prefUserPhone)
        editor.remove(prefUserName)
        editor.remove(prefUserEmail)
        editor.remove(prefUserBalance)
        editor.commit()
    }
}