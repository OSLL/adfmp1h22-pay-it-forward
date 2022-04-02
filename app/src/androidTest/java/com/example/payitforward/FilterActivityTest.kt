package com.example.payitforward

import android.support.test.rule.ActivityTestRule
import org.junit.Rule

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import org.junit.Test

class FilterActivityTest {

    @Rule
    @JvmField

    val activityRule = ActivityTestRule(FilterActivity::class.java)

    @Test
    fun testInit() {
        onView(withId(R.id.filterNew)).check(matches(isDisplayed()))
        onView(withId(R.id.filterCompleted)).check(matches(isDisplayed()))
        onView(withId(R.id.filterOnReview)).check(matches(isDisplayed()))
        onView(withId(R.id.filterInProgress)).check(matches(isDisplayed()))
        onView(withId(R.id.filterAccepted)).check(matches(isDisplayed()))
        onView(withId(R.id.filterRejected)).check(matches(isDisplayed()))
        onView(withId(R.id.filterApply)).check(matches(isDisplayed()))
        onView(withId(R.id.filterCancel)).check(matches(isDisplayed()))

        onView(withId(R.id.filterNew)).check(matches(isNotChecked()))
        onView(withId(R.id.filterCompleted)).check(matches(isNotChecked()))
        onView(withId(R.id.filterOnReview)).check(matches(isNotChecked()))
        onView(withId(R.id.filterInProgress)).check(matches(isNotChecked()))
        onView(withId(R.id.filterAccepted)).check(matches(isNotChecked()))
        onView(withId(R.id.filterRejected)).check(matches(isNotChecked()))
    }

    @Test
    fun testChooseFilters() {
        onView(withId(R.id.filterNew)).perform(click()).check(matches(isChecked()))
        onView(withId(R.id.filterCompleted)).perform(click()).check(matches(isChecked()))
        onView(withId(R.id.filterOnReview)).perform(click()).check(matches(isChecked()))
        onView(withId(R.id.filterInProgress)).perform(click()).check(matches(isChecked()))
        onView(withId(R.id.filterAccepted)).perform(click()).check(matches(isChecked()))
        onView(withId(R.id.filterRejected)).perform(click()).check(matches(isChecked()))
    }

    @Test
    fun testDeleteFiltersAfterChoosing() {
        onView(withId(R.id.filterNew)).perform(click()).check(matches(isChecked()))
        onView(withId(R.id.filterCompleted)).perform(click()).check(matches(isChecked()))
        onView(withId(R.id.filterOnReview)).perform(click()).check(matches(isChecked()))
        onView(withId(R.id.filterInProgress)).perform(click()).check(matches(isChecked()))
        onView(withId(R.id.filterAccepted)).perform(click()).check(matches(isChecked()))
        onView(withId(R.id.filterRejected)).perform(click()).check(matches(isChecked()))

        onView(withId(R.id.filterNew)).perform(click()).check(matches(isNotChecked()))
        onView(withId(R.id.filterCompleted)).perform(click()).check(matches(isNotChecked()))
        onView(withId(R.id.filterOnReview)).perform(click()).check(matches(isNotChecked()))
        onView(withId(R.id.filterInProgress)).perform(click()).check(matches(isNotChecked()))
        onView(withId(R.id.filterAccepted)).perform(click()).check(matches(isNotChecked()))
        onView(withId(R.id.filterRejected)).perform(click()).check(matches(isNotChecked()))
    }

}