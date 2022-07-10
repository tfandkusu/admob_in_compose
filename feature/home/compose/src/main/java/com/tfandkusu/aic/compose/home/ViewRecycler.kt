package com.tfandkusu.aic.compose.home

import android.content.Context
import android.view.View
import android.view.ViewGroup

abstract class ViewRecycler<T : View> {
    private val viewQueue = mutableListOf<T>()

    fun createOrDequeue(context: Context): T {
        return if (viewQueue.isEmpty()) {
            createView(context)
        } else {
            viewQueue.removeAt(0)
        }
    }

    abstract fun createView(context: Context): T

    fun enqueue(view: T) {
        (view.parent as ViewGroup).removeView(view)
        viewQueue.add(view)
    }

    fun clear() {
        viewQueue.clear()
    }
}
