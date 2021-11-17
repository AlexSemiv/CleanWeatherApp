package com.example.common.extensions

import android.app.Activity
import android.util.Log
import androidx.fragment.app.Fragment

object Utils {

    private const val TAG = "DEBUG_TAG"

    fun Fragment.infoLog(message: String) {
        Log.i(TAG, message)
    }

    fun Activity.infoLog(message: String) {
        Log.i(TAG, message)
    }

    fun Fragment.debugLog(message: String) {
        Log.d(TAG, message)
    }

    fun Activity.debugLog(message: String) {
        Log.d(TAG, message)
    }
}