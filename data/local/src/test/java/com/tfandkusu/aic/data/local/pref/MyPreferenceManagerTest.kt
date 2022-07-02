package com.tfandkusu.aic.data.local.pref

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MyPreferenceManagerTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private lateinit var pref: MyPreferenceManager

    @Before
    fun setUp() {
        pref = MyPreferenceManagerImpl(MyPreferenceBuilder.build(context))
        pref.clear()
    }

    @Test
    fun startupTimes() {
        pref.getNumberOfStarts() shouldBe 0
        pref.countUpNumberOfStarts()
        pref.getNumberOfStarts() shouldBe 1
        pref.countUpNumberOfStarts()
        pref.getNumberOfStarts() shouldBe 2
    }
}
