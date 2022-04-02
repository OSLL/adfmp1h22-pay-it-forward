package com.example.payitforward

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import com.example.payitforward.ui.chat.DialogActivity
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test

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