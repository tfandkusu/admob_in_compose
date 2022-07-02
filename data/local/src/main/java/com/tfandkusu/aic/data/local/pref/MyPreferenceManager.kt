package com.tfandkusu.aic.data.local.pref

import android.annotation.SuppressLint
import android.content.SharedPreferences
import javax.inject.Inject

interface MyPreferenceManager {
    fun countUpNumberOfStarts()

    fun getNumberOfStarts(): Int

    fun clear()
}

class MyPreferenceManagerImpl @Inject constructor(
    private val pref: SharedPreferences
) : MyPreferenceManager {

    companion object {
        private const val PREF_NUMBER_OF_STARTS = "numberOfStarts"
    }

    override fun countUpNumberOfStarts() {
        val startupTimes = pref.getInt(PREF_NUMBER_OF_STARTS, 0)
        val editor = pref.edit()
        editor.putInt(PREF_NUMBER_OF_STARTS, startupTimes + 1)
        editor.apply()
    }

    override fun getNumberOfStarts(): Int {
        return pref.getInt(PREF_NUMBER_OF_STARTS, 0)
    }

    @SuppressLint("ApplySharedPref")
    override fun clear() {
        pref.edit().clear().commit()
    }
}
