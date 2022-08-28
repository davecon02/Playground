package com.brightline.beta.screen

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.brightline.beta.R
import com.brightline.beta.features.main.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule

open class BaseScreen {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    sealed class Tabs(val resId : Int) {
        object Home : Tabs(R.id.navigation_home)
        object Book : Tabs(R.id.navigation_book)
        object Trips : Tabs(R.id.navigation_trips)
        object Profile : Tabs(R.id.navigation_profile)
    }

    sealed class Directions(val direction: String) {
        object RIGHT : Directions( "RIGHT")
        object LEFT : Directions("LEFT")
        object UP : Directions( "UP")
        object DOWN : Directions("DOWN")
    }

    val toolbarCloseButton: ViewInteraction = onView(withId(R.id.action_menu_close))

    fun selectTab(tab: Tabs) {
        onView(withId(tab.resId)).perform(ViewActions.click())
    }

    fun swipe(direction: Directions): ViewAction {
        when (direction) {
            Directions.LEFT -> return GeneralSwipeAction(
                Swipe.FAST, GeneralLocation.CENTER,
                GeneralLocation.CENTER_RIGHT, Press.FINGER)
            Directions.RIGHT -> return GeneralSwipeAction(
                Swipe.FAST, GeneralLocation.CENTER,
                GeneralLocation.CENTER_LEFT, Press.FINGER)
            Directions.UP -> return GeneralSwipeAction(
                Swipe.FAST, GeneralLocation.CENTER,
                GeneralLocation.BOTTOM_CENTER, Press.FINGER)
            Directions.DOWN -> return GeneralSwipeAction(
                Swipe.FAST, GeneralLocation.CENTER,
                GeneralLocation.TOP_CENTER, Press.FINGER)
        }
    }


    /**
     * This enables targeting of elements in the view hierarchy seen in Layout Inspector.
     * The native tool bar used in the app prevents the application of resource IDs to its
     * elements. While we can use "withText" and other matchers for text, there exist no
     * clean way for targeting the native up button (back button in the tool bar). We only need
     * to know what position this button has within the toolbar to create a view interaction
     * with this.
     */
    fun childAtPosition(parentMatcher: Matcher<View>, position: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent) && view == parent.getChildAt(position)
            }
        }
    }

    fun enableAirplaneMode(device: UiDevice) = apply{
        device.openQuickSettings()
        device.waitForIdle()

        var modeStatus = checkNotNull(device.findObject(By.descContains("Airplane")))
        modeStatus.click()
        device.click(542,1720)
    }

    // TODO fix this. we're able to enable but not disable
    fun disableAirplaneMode(device: UiDevice) = apply {
        device.openQuickSettings()
        device.waitForIdle()

        var modeStatus = checkNotNull(device.findObject(By.desc("Airplane,mode,On.,Button")))
        modeStatus.click()
        device.click(542,1720)
    }
}