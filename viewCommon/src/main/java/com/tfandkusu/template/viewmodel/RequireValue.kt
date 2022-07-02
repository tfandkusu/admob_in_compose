package com.tfandkusu.template.viewmodel

import androidx.lifecycle.LiveData

fun <T : Any> LiveData<T>.requireValue() = requireNotNull(value)
