package com.tfandkusu.aic.error

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException
import java.lang.IllegalArgumentException

class ApiErrorMapperTest {

    @Test
    fun mapApiError() {
        mapApiError(IOException()).shouldBeTypeOf<NetworkErrorException>()
    }

    @Test
    fun mapApiErrorServerError() {
        val e = mockk<HttpException>()
        every {
            e.code()
        } returns 500
        every {
            e.message()
        } returns "Internal server error"
        val ce = mapApiError(e)
        ce.shouldBeTypeOf<ServerErrorException>()
        if (ce is ServerErrorException) {
            ce.code shouldBe 500
            ce.httpMessage shouldBe "Internal server error"
        }
    }

    @Test
    fun mapApiErrorUnknown() {
        mapApiError(IllegalArgumentException()).shouldBeTypeOf<UnknownErrorException>()
    }
}
