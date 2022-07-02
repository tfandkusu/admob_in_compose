package com.tfandkusu.aic.usecase.info

import com.tfandkusu.aic.data.repository.NumberOfStartsRepository
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.junit.Before
import org.junit.Test

class InfoOnClickAboutUseCaseTest {

    @MockK(relaxed = true)
    private lateinit var repository: NumberOfStartsRepository

    private lateinit var useCase: InfoOnClickAboutUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = InfoOnClickAboutUseCaseImpl(repository)
    }

    @Test
    fun execute() {
        every {
            repository.get()
        } returns 3
        useCase.execute() shouldBe InfoOnClickAboutUseCaseResult(3)
        verifySequence {
            repository.get()
        }
    }
}
