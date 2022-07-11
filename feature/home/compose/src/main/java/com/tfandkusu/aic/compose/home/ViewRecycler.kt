package com.tfandkusu.aic.compose.home

import android.content.Context
import android.view.View
import android.view.ViewGroup

abstract class ViewRecycler<V : View, P> {
    private val viewQueue = mutableListOf<V>()

    fun createOrDequeue(context: Context, param: P): V {
        return if (viewQueue.isEmpty()) {
            createView(context, param)
        } else {
            viewQueue.removeAt(0)
        }
    }

    abstract fun createView(context: Context, param: P): V

    fun enqueue(view: V) {
        (view.parent as ViewGroup).removeView(view)
        viewQueue.add(view)
    }

    fun clear() {
        viewQueue.clear()
    }
}
