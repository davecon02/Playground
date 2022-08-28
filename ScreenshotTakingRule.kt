package com.brightline.beta.utility

import org.junit.rules.TestWatcher
import org.junit.runner.Description

private const val TAG = "Screenshots"

class ScreenshotTakingRule : TestWatcher() {
    override fun failed(e: Throwable?, description: Description) {
        val parentFolderPath = "failedTestScreens/${description.className}"
        takeScreenshot(parentFolderPath = parentFolderPath, screenshotName = description.methodName)
    }
}
