package com.example.payitforward

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ItemTaskActivityTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(ItemTaskActivity::class.java)


    @Test
    fun testTaskWithCompletedType() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("taskId", "K5pdckS2mXTjZWs8JQZyIIWR2Ks21650022149")
        intent.putExtra("creationDate", "")
        intent.putExtra("deadlineDate", "10.05")
        intent.putExtra("authorId", "K5pdckS2mXTjZWs8JQZyIIWR2Ks2")
        intent.putExtra("completedId", "6SSb9c9J98SMCWRMtkrkyHyGYPP2")
        intent.putExtra("name", "Choose a gift")
        intent.putExtra("description", "A toy for a child")
        intent.putExtra("imageUrl", "")
        intent.putExtra("coins", 65)
        intent.putExtra("taskType", "completed")
        activityRule.launchActivity(intent)
        onView(ViewMatchers.withId(R.id.taskImageView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.taskName))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.deadlineTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.deadlineDate))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.authorTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.taskDescriptionTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.coinsImageView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.coinsTextView))
            .check(ViewAssertions.matches(isDisplayed()))
         onView(ViewMatchers.withId(R.id.buttonEdit))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.currentStatusValue))
            .check(ViewAssertions.matches(
                withText("The task was completed by user")))
    }

    @Test
    fun testTaskWithNewType() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("taskId", "K5pdckS2mXTjZWs8JQZyIIWR2Ks21650022149")
        intent.putExtra("creationDate", "")
        intent.putExtra("deadlineDate", "10.05")
        intent.putExtra("authorId", "K5pdckS2mXTjZWs8JQZyIIWR2Ks2")
        intent.putExtra("completedId", "6SSb9c9J98SMCWRMtkrkyHyGYPP2")
        intent.putExtra("name", "Choose a gift")
        intent.putExtra("description", "A toy for a child")
        intent.putExtra("imageUrl", "")
        intent.putExtra("coins", 65)
        intent.putExtra("taskType", "new")
        activityRule.launchActivity(intent)
        onView(ViewMatchers.withId(R.id.taskImageView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.taskName))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.deadlineTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.deadlineDate))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.authorTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.taskDescriptionTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.coinsImageView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.coinsTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.buttonEdit))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.currentStatusValue))
            .check(ViewAssertions.matches(
                withText("The task is created by you and waiting for the executor")))
    }

    @Test
    fun testTaskWithInProgressType() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("taskId", "K5pdckS2mXTjZWs8JQZyIIWR2Ks21650022149")
        intent.putExtra("creationDate", "")
        intent.putExtra("deadlineDate", "10.05")
        intent.putExtra("authorId", "K5pdckS2mXTjZWs8JQZyIIWR2Ks2")
        intent.putExtra("completedId", "6SSb9c9J98SMCWRMtkrkyHyGYPP2")
        intent.putExtra("name", "Choose a gift")
        intent.putExtra("description", "A toy for a child")
        intent.putExtra("imageUrl", "")
        intent.putExtra("coins", 65)
        intent.putExtra("taskType", "inProgress")
        activityRule.launchActivity(intent)
        onView(ViewMatchers.withId(R.id.taskImageView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.taskName))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.deadlineTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.deadlineDate))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.authorTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.taskDescriptionTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.coinsImageView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.coinsTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.buttonEdit))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.currentStatusValue))
            .check(ViewAssertions.matches(
                withText("The task is in progress by user")))
    }

    @Test
    fun testTaskWithOnReviewType() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("taskId", "K5pdckS2mXTjZWs8JQZyIIWR2Ks21650022149")
        intent.putExtra("creationDate", "")
        intent.putExtra("deadlineDate", "10.05")
        intent.putExtra("authorId", "K5pdckS2mXTjZWs8JQZyIIWR2Ks2")
        intent.putExtra("completedId", "6SSb9c9J98SMCWRMtkrkyHyGYPP2")
        intent.putExtra("name", "Choose a gift")
        intent.putExtra("description", "A toy for a child")
        intent.putExtra("imageUrl", "")
        intent.putExtra("coins", 65)
        intent.putExtra("taskType", "onReview")
        activityRule.launchActivity(intent)
        onView(ViewMatchers.withId(R.id.taskImageView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.taskName))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.deadlineTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.deadlineDate))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.authorTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.taskDescriptionTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.coinsImageView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.coinsTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.buttonEdit))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.buttonReject))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.buttonAccept))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun testTaskWithAcceptedType() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("taskId", "K5pdckS2mXTjZWs8JQZyIIWR2Ks21650022149")
        intent.putExtra("creationDate", "")
        intent.putExtra("deadlineDate", "10.05")
        intent.putExtra("authorId", "K5pdckS2mXTjZWs8JQZyIIWR2Ks2")
        intent.putExtra("completedId", "6SSb9c9J98SMCWRMtkrkyHyGYPP2")
        intent.putExtra("name", "Choose a gift")
        intent.putExtra("description", "A toy for a child")
        intent.putExtra("imageUrl", "")
        intent.putExtra("coins", 65)
        intent.putExtra("taskType", "accepted")
        activityRule.launchActivity(intent)
        onView(ViewMatchers.withId(R.id.taskImageView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.taskName))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.deadlineTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.deadlineDate))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.authorTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.taskDescriptionTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.coinsImageView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.coinsTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.buttonEdit))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.currentStatusValue))
            .check(ViewAssertions.matches(
                withText("The task was accepted by you")))
    }

    @Test
    fun testTaskWithRejectedType() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("taskId", "K5pdckS2mXTjZWs8JQZyIIWR2Ks21650022149")
        intent.putExtra("creationDate", "")
        intent.putExtra("deadlineDate", "10.05")
        intent.putExtra("authorId", "K5pdckS2mXTjZWs8JQZyIIWR2Ks2")
        intent.putExtra("completedId", "6SSb9c9J98SMCWRMtkrkyHyGYPP2")
        intent.putExtra("name", "Choose a gift")
        intent.putExtra("description", "A toy for a child")
        intent.putExtra("imageUrl", "")
        intent.putExtra("coins", 65)
        intent.putExtra("taskType", "rejected")
        activityRule.launchActivity(intent)
        onView(ViewMatchers.withId(R.id.taskImageView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.taskName))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.deadlineTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.deadlineDate))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.authorTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.taskDescriptionTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.coinsImageView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.coinsTextView))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.buttonEdit))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.currentStatusValue))
            .check(ViewAssertions.matches(
                withText("The task was rejected by you")))
    }

}