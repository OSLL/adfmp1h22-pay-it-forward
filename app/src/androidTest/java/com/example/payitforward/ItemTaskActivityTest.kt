package com.example.payitforward

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.rule.ActivityTestRule
import androidx.test.espresso.assertion.ViewAssertions.matches
import com.google.firebase.Timestamp
import junit.framework.AssertionFailedError
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeoutException

class ItemTaskActivityTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(ItemTaskActivity::class.java)


    @Test
    fun testTaskWithCompletedType() {
        var intent = Intent(Intent.ACTION_PICK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("taskId", "K5pdckS2mXTjZWs8JQZyIIWR2Ks21650022149")
        intent.putExtra("creationDate", "")
        intent.putExtra("deadlineDate",  Timestamp.now().seconds)
        intent.putExtra("authorId", "K5pdckS2mXTjZWs8JQZyIIWR2Ks2")
        intent.putExtra("completedId", "6SSb9c9J98SMCWRMtkrkyHyGYPP2")
        intent.putExtra("name", "Choose a gift")
        intent.putExtra("description", "A toy for a child")
        intent.putExtra("imageUrl", "")
        intent.putExtra("coins", 65)
        intent.putExtra("taskType", "completed")
        activityRule.launchActivity(intent)
      //  activityRule.runOnUiThread {
        /*
           onView(ViewMatchers.withId(R.id.taskImageView))
            .check(ViewAssertions.matches(isDisplayed()))
            onView(ViewMatchers.withId(R.id.taskName))
                .check(ViewAssertions.matches(isDisplayed()))
            onView(ViewMatchers.withId(R.id.deadlineTextView))
                .check(ViewAssertions.matches(isDisplayed()))
            onView(ViewMatchers.withId(R.id.deadlineDate))
                .check(ViewAssertions.matches(isDisplayed()))
            onView(ViewMatchers.withId(R.id.authorNameTextView))
                .check(ViewAssertions.matches(isDisplayed()))
            onView(ViewMatchers.withId(R.id.authorTextView))
                .check(ViewAssertions.matches(isDisplayed()))
            onView(ViewMatchers.withId(R.id.descriptionTextView))
                .check(ViewAssertions.matches(isDisplayed()))
            onView(ViewMatchers.withId(R.id.coinsImageView))
                .check(ViewAssertions.matches(isDisplayed()))
            onView(ViewMatchers.withId(R.id.coinsTextView))
                .check(ViewAssertions.matches(isDisplayed()))
            onView(ViewMatchers.withId(R.id.coins))
                .check(ViewAssertions.matches(isDisplayed()))*/
        }
        //activityRule.finishActivity()
       // onView(ViewMatchers.withId(R.id.buttonEdit))
       //    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
}