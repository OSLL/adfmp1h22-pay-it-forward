package com.example.payitforward

import androidx.test.ext.junit.runners.AndroidJUnit4
import android.accessibilityservice.AccessibilityService
import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.*
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasType
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import android.view.View
import android.widget.SeekBar
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.equalTo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
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