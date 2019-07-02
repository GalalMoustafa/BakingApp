package com.galalmostafa.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityTest {

    private String[] names = {"Nutella Pie","Brownies","Yellow Cake","Cheesecake"};
    private CountingIdlingResource idlingResource;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRuleTest = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp(){
        idlingResource = mActivityRuleTest.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Test
    public void TestingMainRecyclerView(){
        //check names displayed correctly in recycler
        for (int i = 0; i < 4 ; i++){
            onView(new RecyclerViewMatcher(R.id.recipe_recycler).atPositionOnView(i, R.id.recipe_name))
                    .check(matches(withText(names[i])));
        }

        //perform click on first item
        onView(withId(R.id.recipe_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //check if the ingredients list and steps list displayed
        onView(withId(R.id.ingredientsRecycler)).check(matches(isDisplayed()));
        onView(withId(R.id.stepsRecycler)).check(matches(isDisplayed()));

        //perform click on the first step
        onView(withId(R.id.stepsRecycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //check if the description is displayed
        onView(withId(R.id.step_detail)).check(matches(isDisplayed()));
    }

    @After
    public void ReleaseIdlingResources(){
        IdlingRegistry.getInstance().unregister(idlingResource);
    }

}