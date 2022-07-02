package com.tfandkusu.aic.error

/**
 * Server error
 */
class ServerErrorException(val code: Int, val httpMessage: String) : Exception()
