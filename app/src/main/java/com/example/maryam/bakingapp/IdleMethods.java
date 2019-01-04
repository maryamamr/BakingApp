package com.example.maryam.bakingapp;

import android.support.test.espresso.IdlingResource;

public class IdleMethods {

    private static Ideling IdlingResource;

    public static IdlingResource getIdlingResource() {
        if (IdlingResource == null) {
            IdlingResource = new Ideling();
        }
        return IdlingResource;
    }

}
