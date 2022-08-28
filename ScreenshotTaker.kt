package com.brightline.beta.utility

import androidx.test.runner.screenshot.Screenshot
import timber.log.Timber
import java.io.IOException

private const val TAG = "Screenshots"

fun takeScreenshot(parentFolderPath: String = "", screenshotName: String) {
    Timber.d("[$TAG]: Taking screenshot of $screenshotName")

    val capture = Screenshot.capture()
    val processors = setOf(ScreenCaptureProcessor(parentFolderPath))

    try {
        capture.apply {
            name = screenshotName
            process(processors)
        }
    } catch (exception: IOException){
        Timber.d("[$TAG]: Could not capture screenshot due to exception: ${exception.message}")
    }
}