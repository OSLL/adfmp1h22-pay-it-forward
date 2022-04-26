package com.example.payitforward

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import com.example.payitforward.ui.statistics.StatisticsFragment
import com.example.payitforward.util.FirestoreUtil
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class StatisticsFragmentTest {

    private lateinit var navController: TestNavHostController
    private val theme = R.style.Theme_PayItForward

    @Before
    fun setUpTest() {

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        UiThreadStatement.runOnUiThread {
            navController.setGraph(R.navigation.mobile_navigation)
            navController.setCurrentDestination(R.id.nav_statistics)
        }.let {
            launchFragmentInContainer(themeResId = theme) {
                StatisticsFragment().also { fragment ->
                    fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                        if (viewLifecycleOwner != null) {
                            Navigation.setViewNavController(fragment.requireView(), navController)
                        }
                    }
                }
            }
        }
        FirestoreUtil.setUserId("K5pdckS2mXTjZWs8JQZyIIWR2Ks2")
    }

    @Test
    fun checkChartButtons() {
        onView(withId(R.id.Week)).check(matches(withText("Last Week")))
        onView(withId(R.id.Month)).check(matches(withText("Last Month")))
        onView(withId(R.id.Month)).check(matches(isEnabled()))
        onView(withId(R.id.Week)).check(matches(isEnabled()))
        onView(withId(R.id.Month)).perform(ViewActions.click())
    }

    @Test
    fun checkChart() {
        onView(withId(R.id.barChart)).check(matches(isDisplayed()))
    }

    @Test
    fun checkSearch() {
        onView(withId(R.id.searchText)).check(matches(withHint("search")))
        onView(withId(R.id.sendButton)).check(matches(isEnabled()))
        onView(withId(R.id.searchText)).perform(ViewActions.typeText("find"))
    }

    @Test
    fun checkHistory() {
        onView(withId(R.id.history)).check(matches(isDisplayed()))
    }

}
