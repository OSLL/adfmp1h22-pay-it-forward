package com.example.payitforward

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.view.View
import android.widget.SeekBar
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test


class CreateDeedTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(CreateDeedActivity::class.java)

    @Test
    fun testSeekBar() {
        onView(withId(R.id.seekBar)).perform(setProgress(10))
        onView(withId(R.id.coinsTextView)).check(matches(withText("10")))
    }

    private fun setProgress(progress: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(SeekBar::class.java)
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
}