package com.brightline.beta.utility

import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matcher

/*
    These functions act as methods on ViewInteractions during tests.
    Placing these in BaseScreen prevents them from being called during tests.
 */

sealed class Direction(val direction: ViewAction) {
    object Up: Direction(swipeUp())
    object Down: Direction(swipeDown())
    object Left: Direction(swipeLeft())
    object Right: Direction(swipeRight())
}

fun ViewInteraction.click() {
    perform(ViewActions.click())
}

fun ViewInteraction.waitUntilVisible(timeout: Long = 10000) {
    val startTime = System.currentTimeMillis()
    val endTime = startTime + timeout

    // This loop will try to check something is displayed and catch
    // the error that occurs if it is not, then wait 50 ms and repeat
    do {
        try {
            check(matches(isDisplayed()))
            return
        } catch (t: Throwable) {
            Thread.sleep(50)
        }
    } while (System.currentTimeMillis() < endTime)
    check(matches(isDisplayed()))
}

// TODO look into customScroll, these methods won't work for nestScrollViews
// Most reliable scroll method
fun ViewInteraction.scrollToIndex(index: Int) {
    perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(index))
}

// Centers the view on the screen, if not on the screen, fails
fun ViewInteraction.scrollTo() {
    perform(ViewActions.scrollTo())
}

// The id has to be a direct child of the recyclerview or this fails
fun ViewInteraction.scrollToId(@IdRes childId: Int) {
    perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
        withId(childId)
    ))
}

val ViewInteraction.text : String get() {
    waitUntilVisible()
    var text = String()
    perform(object: ViewAction {
        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isAssignableFrom(TextView::class.java)
        }

        override fun getDescription(): String {
            return "Text of the view"
        }

        override fun perform(uiController: UiController?, view: View?) {
            val tv = view as TextView
            text = tv.text.toString()
        }
    })

    return text
}

fun ViewInteraction.enterText(text: String) {
    waitUntilVisible()
    perform(ViewActions.typeText(text))
}

fun ViewInteraction.swipe(direction: Direction) {
    perform(direction.direction)
}