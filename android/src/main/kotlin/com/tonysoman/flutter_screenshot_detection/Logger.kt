package com.tonysoman.flutter_screenshot_detection

import android.util.Log

class Logger private constructor() {

    companion object {
        val shared: Logger by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { Logger() }

    }

    fun debug(message: String = "") {
        Log.d("Logger", message)
    }

    fun error(message: String = "", error: Throwable?) {
        Log.e("Logger", message, error)
    }

}