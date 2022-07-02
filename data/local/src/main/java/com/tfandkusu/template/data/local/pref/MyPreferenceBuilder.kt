package com.tfandkusu.template.data.local.pref

import android.content.Context
import android.content.SharedPreferences

object MyPreferenceBuilder {
    fun build(applicationContext: Context): SharedPreferences {
        return applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
    }
}
