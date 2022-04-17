package com.example.payitforward

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import com.example.payitforward.ui.chat.ChatFragment
import com.example.payitforward.util.FirestoreUtil
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ChatFragmentTest {

    private lateinit var navController: TestNavHostController
    private val theme = R.style.Theme_PayItForward

    @Before
    fun setUpTest() {

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        UiThreadStatement.runOnUiThread {
            navController.setGraph(R.navigation.mobile_navigation)
            navController.setCurrentDestination(R.id.nav_chat)
        }.let {
            launchFragmentInContainer(themeResId = theme) {
                ChatFragment().also { fragment ->
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
    fun checkHintIsSearch() {
        onView(withId(R.id.searchText)).check(matches(withHint("search")))
        onView(withId(R.id.search)).check(matches(isDisplayed()))
    }

    @Test
    fun checkButton() {
        onView(withId(R.id.sendButton)).check(matches(isEnabled()))
    }

    @Test
    fun checkListDialogs() {
        onView(withId(R.id.list_dialogs)).check(matches(isDisplayed()))
    }

}