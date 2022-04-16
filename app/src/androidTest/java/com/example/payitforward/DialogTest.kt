package com.example.payitforward

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.payitforward.ui.chat.DialogActivity
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DialogTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(DialogActivity::class.java)

    @Test
    fun testInit() {
        onView(withId(R.id.messageRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.messageEditText)).check(matches(isDisplayed()))
        onView(withId(R.id.sendButton)).check(matches(isDisplayed()))
        onView(withId(R.id.addMessageImageView)).check(matches(isDisplayed()))
    }

    @Test
    fun testSendMessage() {
        onView(withId(R.id.messageEditText)).perform(typeText("Hello!"))
        onView(withId(R.id.sendButton)).perform(click())
        onView(withId(R.id.messageRecyclerView)).check(matches(hasDescendant(withText("Hello!"))))
    }

    @Test
    fun testImage() {
        Intents.init()
        onView(withId(R.id.addMessageImageView)).perform(click())
        Intents.intended(
            Matchers.allOf(
                IntentMatchers.hasAction(Matchers.equalTo(Intent.ACTION_PICK)),
                IntentMatchers.hasType("image/*")
            )
        )
        Intents.release()
        InstrumentationRegistry.getInstrumentation()
            .uiAutomation
            .performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
    }

}