package jp.bizen.android.vrchat.oscmessenger.util

import android.content.Context
import androidx.core.content.edit

class Pref(context: Context) {
    companion object {
        private const val PREF_NAME = "messenger_data"
        private const val PREF_KEY_IP_ADDRESS = "ip_address"
    }
    private val preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var ipAddress: String
        get() = preference.getString(PREF_KEY_IP_ADDRESS, "")!!
        set(value) = preference.edit { putString(PREF_KEY_IP_ADDRESS, value) }
}
