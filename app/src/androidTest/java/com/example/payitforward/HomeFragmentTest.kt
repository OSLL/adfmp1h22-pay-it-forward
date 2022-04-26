package com.example.payitforward

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
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