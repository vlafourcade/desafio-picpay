package com.picpay.desafio.android.core.utils.logging

import android.util.Log

interface Logger {
    fun d(message: String)

    fun e(message: String, throwable: Throwable)

    fun i(message: String)

    fun w(message: String)
}

internal class LoggerImpl : Logger {
    private val TAG = "desafio-picpay"

    override fun d(message: String) {
        Log.d(TAG, message)
    }

    override fun e(message: String, throwable: Throwable) {
        Log.e(TAG, message, throwable)
    }

    override fun i(message: String) {
        Log.i(TAG, message)
    }

    override fun w(message: String) {
        Log.w(TAG, message)
    }

}