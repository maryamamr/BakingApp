package com.example.maryam.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityRecipeRecyclerViewTest {
    private  Ideling mIdle;
    @Rule public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
     public void setIdle(){
        mIdle= (Ideling)IdleMethods.getIdlingResource();
        IdlingRegistry.getInstance().register(mIdle);
    }
    @Test
    public void clickedRecipeToOpenInfo(){
        onView(withId(R.id.recipes_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdle != null) {
            IdlingRegistry.getInstance().unregister(mIdle);
        }
    }

}
