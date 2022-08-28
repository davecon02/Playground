package com.brightline.beta.test

import android.Manifest
import androidx.annotation.IdRes
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.PositionAssertions.isCompletelyLeftOf
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.brightline.beta.features.main.MainActivity
import com.brightline.beta.screen.*
import com.brightline.beta.utility.ScreenshotTakingRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.RuleChain

@HiltAndroidTest
open class BaseTest {

    val baseScreen = BaseScreen()
    val homeScreen = HomeScreen()
    val bookScreen = BookScreen()
    val tripScreen = TripScreen()
    val profileScreen = ProfileScreen()
    val originAndDestinationScreen = OriginAndDestinationScreen()
    val passengerSelectionScreen = PassengerSelectionScreen()
    val dateSelectionScreen = DateSelectionScreen()

    // Permissions needed for capturing screenshots upon test failure on emulator
    private val grantPermissionsRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    // Rule chain is needed to ensure that screenshot is captured before activity dies
    @get:Rule
    val ruleChain: RuleChain = RuleChain
        .outerRule(HiltAndroidRule(this))
        .around(grantPermissionsRule)
        .around(ActivityScenarioRule(MainActivity::class.java))
        .around(ScreenshotTakingRule())

    @Before
    fun init() {
        // Init any necessary resources for test
    }

    @After
    fun teardown() {
        // After tests run, destroy any necessary resources for test for re-init
    }

    fun assertTextMatches(interaction: ViewInteraction, expected: String) {
        interaction.check(matches(withText(expected)))
    }

    fun assertElementIsSelected(interaction: ViewInteraction) {
        interaction.check(matches(isSelected()))
    }

    fun assertElementIsLeftOfId(interaction: ViewInteraction, @IdRes rightId:Int) {
        interaction.check(isCompletelyLeftOf(withId(rightId)))
    }

    fun assertElementIsDisplayed(interaction: ViewInteraction) {
        interaction.check(matches(isDisplayed()))
    }

    fun assertElementIsClickable(interaction: ViewInteraction) {
        interaction.check(matches(isClickable()))
    }

    fun assertTextDoesNotMatch(interaction: ViewInteraction, compare: String) {
        interaction.check(matches(not(withText(compare))))
    }

    fun assertWebViewOpens() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        assertThat(device, CoreMatchers.notNullValue())
        device.wait(Until.findObject(By.pkg("com.android.chrome")), 7500)
        assert(device.currentPackageName == "com.android.chrome")
    }

    fun assertElementNotDisplayed(interaction: ViewInteraction) {
        interaction.check(matches(not(isDisplayed())))
    }

    fun assertElementNotEnabled(interaction: ViewInteraction) {
        interaction.check(matches(isEnabled()))
    }
}