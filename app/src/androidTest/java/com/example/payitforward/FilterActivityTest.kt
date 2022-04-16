package com.example.payitforward

import androidx.test.rule.ActivityTestRule
import org.junit.Rule

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
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