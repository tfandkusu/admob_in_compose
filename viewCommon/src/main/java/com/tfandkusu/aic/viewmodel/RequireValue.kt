package com.tfandkusu.aic.viewmodel

import androidx.lifecycle.LiveData

fun <T : Any> LiveData<T>.requireValue() = requireNotNull(value)
