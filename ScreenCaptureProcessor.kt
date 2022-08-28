package com.brightline.beta.utility

import android.os.Environment
import androidx.test.runner.screenshot.BasicScreenCaptureProcessor
import java.io.File

class ScreenCaptureProcessor(parentFolderPath: String) : BasicScreenCaptureProcessor() {
    init {
        this.mDefaultScreenshotPath = File(
            File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES),
                // Main directory name on emulator to store failedTestScreens
                "BLTrains"
            ).absolutePath,
            "screenshots/$parentFolderPath"
        )
    }
}