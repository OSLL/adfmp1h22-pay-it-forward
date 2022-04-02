package com.example.payitforward

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents.*
import android.support.test.espresso.intent.matcher.IntentMatchers.hasAction
import android.support.test.espresso.intent.matcher.IntentMatchers.hasType
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.view.View
import android.widget.SeekBar
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.equalTo
import org.junit.Rule
import org.junit.Test


class CreateDeedTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(CreateDeedActivity::class.java)

    @Test
    fun testInit() {
        onView(withId(R.id.changePhoto)).check(matches(isDisplayed()))
        onView(withId(R.id.titleEditText)).check(matches(isDisplayed()))
        onView(withId(R.id.titleTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.descriptionTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.descriptionEditText)).check(matches(isDisplayed()))
        onView(withId(R.id.pointsTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.coinsTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.seekBar)).check(matches(isDisplayed()))
        onView(withId(R.id.deadlineTextView)).check(matches(isDisplayed()))
    }

    @Test
    fun testSeekBar() {
        onView(withId(R.id.seekBar)).perform(setProgress(10))
        onView(withId(R.id.coinsTextView)).check(matches(withText("10")))
        onView(withId(R.id.seekBar)).perform(setProgress(20))
        onView(withId(R.id.coinsTextView)).check(matches(withText("20")))
    }

    private fun setProgress(progress: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(SeekBar::class.java)
            }

            override fun getDescription(): String {
                return "Set a progress on a SeekBar"
            }

            override fun perform(uiController: UiController?, view: View) {
                val seekBar = view as SeekBar
                seekBar.progress = progress
            }
        }
    }

    @Test
    fun testPhoto() {
        init()
        onView(withId(R.id.changePhoto)).perform(click())
        intended(allOf(hasAction(equalTo(Intent.ACTION_PICK)), hasType("image/*")))
        release()
        InstrumentationRegistry.getInstrumentation()
            .uiAutomation
            .performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
    }

}