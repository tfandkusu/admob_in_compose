package com.tfandkusu.template.data.repository

import com.tfandkusu.template.data.local.pref.MyPreferenceManager
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.junit.Before
import org.junit.Test

class NumberOfStartsRepositoryTest {

    @MockK(relaxed = true)
    private lateinit var pref: MyPreferenceManager

    private lateinit var repository: NumberOfStartsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = NumberOfStartsRepositoryImpl(pref)
    }

    @Test
    fun countUp() {
        repository.countUp()
        verifySequence {
            pref.countUpNumberOfStarts()
        }
    }

    @Test
    fun get() {
        every {
            pref.getNumberOfStarts()
        } returns 3
        repository.get() shouldBe 3
        verifySequence {
            pref.getNumberOfStarts()
        }
    }
}
