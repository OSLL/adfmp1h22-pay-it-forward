package com.example.payitforward

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test

class HomeFragmentTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MenuActivity::class.java)

    @Test
    fun testInit() {
        onView(ViewMatchers.withId(R.id.filterButtonHome))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.searchHome))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.sendButtonHome))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.fab))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.tab_layout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.tasks_recycler_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testSearch() {
        onView(ViewMatchers.withId(R.id.searchHome)).perform(ViewActions.typeText("Hello!"))
        onView(ViewMatchers.withId(R.id.searchHome)).perform(ViewActions.click())
    }

    @Test
    fun testOpenFilters() {
        onView(ViewMatchers.withId(R.id.filterButtonHome)).perform(ViewActions.click())
    }

    @Test
    fun testClickFab() {
        onView(ViewMatchers.withId(R.id.fab)).perform(ViewActions.click())
    }
}