package com.simplekjl.fallingwords


import android.view.View
import android.view.ViewGroup
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class GameJourney {


    private var mIdlingResource: IdlingResource? = null

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.onActivity { activity ->
            mIdlingResource = activity.getIdlingResource()
            // To prove that the test fails, omit this call:
            IdlingRegistry.getInstance().register(mIdlingResource)
        }
    }

    @Test
    fun gameJourney() {
        val startGameButton = onView(
            allOf(
                withId(R.id.start_btn), withText("Start game"),
                childAtPosition(
                    allOf(
                        withId(R.id.content_view),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    17
                ),
                isDisplayed()
            )
        )
        startGameButton.perform(click())

        val cardBottom = onView(
            allOf(
                withId(R.id.card_word_guess),
                withParent(
                    allOf(
                        withId(R.id.content_view),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        cardBottom.check(matches(isDisplayed()))


        val cardFalling = onView(
            allOf(
                withId(R.id.card_word_uns),
                withParent(
                    allOf(
                        withId(R.id.content_view),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        cardFalling.check(matches(isDisplayed()))

        val positiveBtn = onView(
            allOf(
                withId(R.id.positive_btn), withContentDescription("ok button"),
                withParent(
                    allOf(
                        withId(R.id.content_view),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        positiveBtn.check(matches(isDisplayed()))


        val negativeBtn = onView(
            allOf(
                withId(R.id.negative_btn), withContentDescription("negative button"),
                withParent(
                    allOf(
                        withId(R.id.content_view),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        negativeBtn.check(matches(isDisplayed()))

    }

    @After
    fun unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource)
        }
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
